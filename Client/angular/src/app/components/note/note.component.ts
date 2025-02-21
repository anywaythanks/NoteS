import {Component, HostListener, inject, Input, OnDestroy, OnInit} from '@angular/core';
import {Note, NoteSaveOther} from "../../models/note.model";
import {NoteService} from "../../services/note.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {
  faBookOpen,
  faClock,
  faEye,
  faEyeSlash,
  faFloppyDisk,
  faGlasses,
  faLink,
  faPen,
  faTrash,
  faUser
} from "@fortawesome/free-solid-svg-icons";
import {HttpErrorResponse} from "@angular/common/http";
import {AngularMarkdownEditorModule, EditorOption} from 'angular-markdown-editor';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MarkdownComponent, MarkdownService} from "ngx-markdown";
import {forkJoin, Observable, Subject, tap} from "rxjs";
import {UserService} from "../../services/keycloak-profile.service";
import {Anonymous, User} from "../../models/user.model";
import {Router} from "@angular/router";
import {CommentsComponent} from "../comments/comments.component";
import {parseKatex} from "../../helpers/parse.helper";
import {TagsComponent} from "../tags/tags.component";
import {CopyComponent} from "../copy/copy.component";
import {MenuComponent} from "../menu/menu.component";
import {Title} from "@angular/platform-browser";
import {FooterComponent} from "../footer/footer.component";
import {MatFormField} from "@angular/material/form-field";
import {MatInput} from "@angular/material/input";

@Component({
  selector: 'app-note',
  templateUrl: './note.component.html',
  standalone: true,
  imports: [
    FaIconComponent,
    AngularMarkdownEditorModule,
    FormsModule,
    MarkdownComponent,
    ReactiveFormsModule,
    CommentsComponent,
    TagsComponent,
    CopyComponent,
    MenuComponent,
    FooterComponent,
    MatFormField,
    MatInput
  ],
  styleUrls: ['./note.component.scss'],
})
export class NoteComponent implements OnInit, OnDestroy {
  @Input() uuid!: string;
  note: Note | undefined;
  title: string = "";
  editorOptions!: EditorOption;
  markdownText = '';
  templateForm!: FormGroup;
  private destroy$ = new Subject<void>();
  // private readonly AUTOSAVE_INTERVAL = 30000; // 30 seconds
  isEditMode = false;
  isOwner = false;
  isShowPreview = false;
  isPublic = false;
  description: string | undefined;
  private userService = inject(UserService);
  hasUnsavedChangesProp: boolean = false;

  constructor(private titleService: Title, private router: Router, private fb: FormBuilder, private markdownService: MarkdownService, private readonly noteService: NoteService) {
  }

  buildForm(markdownText: string) {
    this.templateForm = this.fb.group({
      body: [markdownText],
      isPreview: [true]
    });
  }

  saveChanges() {
    if (!this.hasUnsavedChangesProp) return;

    const requests: Observable<Note>[] = [];

    if (this.note?.note_type === 'NOTE' && this.note?.content !== this.markdownText) {
      requests.push(
        this.noteService.saveContentNote(
          {content: this.markdownText, syntax_name: "markdown"},
          this.uuid
        ).pipe(
          tap((note) => {
            if (this.note) this.note.content = note.content || "";
            this.checkAndChangeVals();
          })
        )
      );
    }
    if ((this.note?.note_type === 'COMMENT' || this.note?.note_type === 'COMMENT_REDACTED')
      && this.note?.content !== this.markdownText) {
      requests.push(
        this.noteService.saveContentComment(
          {content: this.markdownText, syntax_name: "markdown", title: this.title},
          this.uuid
        ).pipe(
          tap((note) => {
            if (this.note) this.note.content = note.content || "";
            this.checkAndChangeVals();
          })
        )
      );
    }
    if (this.note?.note_type === 'NOTE' && this.note?.is_public !== this.isPublic) {
      requests.push(
        this.noteService.publicNote(this.isPublic, this.uuid).pipe(
          tap((note) => {
            if (this.note) this.note.is_public = this.isPublic;
            this.checkAndChangeVals();
          })
        )
      );
    }

    if (this.note?.note_type === 'NOTE' && this.note?.description !== this.description || this.note?.title !== this.title) {
      requests.push(
        this.noteService.saveNote(
          new NoteSaveOther(this.title, this.description || this.note?.description || ""),
          this.uuid
        ).pipe(
          tap((note) => {
            if (this.note) {
              this.note.description = note.description;
              this.note.title = note.title;
              this.titleService.setTitle(this.title);
            }
            this.checkAndChangeVals();
          })
        )
      );
    }

    if (requests.length === 0) return;

    forkJoin(requests)
      .subscribe(() => {
        // this.checkAndChangeVals();
      });
  }

  @HostListener('window:beforeunload', ['$event'])
  handleBeforeUnload(event: BeforeUnloadEvent) {
    if (this.isOwner && this.hasUnsavedChangesProp) {
      event.preventDefault();
    }
  }

  hasUnsavedChanges(): boolean {
    if (!this.isOwner) return false;
    return this.markdownText !== this.note?.content ||
      this.title !== this.note?.title ||
      this.isPublic !== this.note?.is_public ||
      this.description !== this.note?.description;
  }

  pageUrl() {
    return window.location.href;
  }

  ngOnInit() {
    this.editorOptions = {
      autofocus: true,
      iconlibrary: 'fa',
      savable: false,
      onChange: () => this.changeVals(),
      parser: () => this.parse(this.markdownText),
      onFullscreenExit: () => this.hidePreview(),
    };
    this.noteService.getNote(this.uuid).subscribe({
      next: (note) => this.userService.getUser().subscribe(user => this.init(note, user)),
      error: (err: HttpErrorResponse) => {
        if (err.status == 403) {
          this.router.navigate(['/forbidden']);
        } else if (err.status == 404) {
          this.router.navigate(['/notfound']);
        }
      }
    });
    // Setup autosave
    // interval(this.AUTOSAVE_INTERVAL).pipe(
    //   takeUntil(this.destroy$),
    //   throttleTime(1000) // Prevent rapid successive calls
    // ).subscribe(() => {
    //   if (this.hasUnsavedChanges) {
    //     this.saveChanges();
    //   }
    // });
  }

  init(note: Note, user: User | Anonymous) {
    this.note = note
    this.title = note.title;
    this.titleService.setTitle(this.title);
    this.isPublic = note.is_public;
    this.description = note.description;
    this.isOwner = user.type != 'anonymous' && this.note.owner_account_name == user.username;
    this.markdownText = note.content;

    this.buildForm(this.markdownText)
  }

  ngOnDestroy() {
    this.destroy$.next();
    this.destroy$.complete();
  }

  togglePublic() {
    this.changeVals();
  }

  toggleEdit() {
  }

  togglePreview() {

  }

  deleteNote() {
    if (confirm('Are you sure you want to delete this?')) {
      this.noteService.deleteNote(this.uuid).subscribe(_ => this.router.navigate(['/']));
    }
  }

  hidePreview() {
    console.log("hide?");
  }

  parse(inputValue: string) {
    let markedOutput = this.markdownService.parse(inputValue.trim());
    if (typeof markedOutput === 'string') {
      markedOutput = parseKatex(markedOutput)
    } else {
      markedOutput.then(text =>
        markedOutput = parseKatex(text)
      )
    }

    this.highlight();

    return markedOutput;
  }

  highlight() {
    setTimeout(() => {
      this.markdownService.highlight();
    });
  }

  checkAndChangeVals() {
    this.hasUnsavedChangesProp = this.hasUnsavedChanges()
  }

  changeVals() {
    if (this.hasUnsavedChangesProp) return;
    this.hasUnsavedChangesProp = true
  }

  protected readonly faUser = faUser;
  protected readonly faClock = faClock;
  protected readonly faEye = faEye;
  protected readonly faEyeSlash = faEyeSlash;
  protected readonly faPen = faPen;
  protected readonly faLink = faLink;
  protected readonly faBookOpen = faBookOpen;
  protected readonly faGlasses = faGlasses;
  protected readonly faTrash = faTrash;
  protected readonly faFloppyDisk = faFloppyDisk;
}
