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
import {Subject} from "rxjs";
import {UserService} from "../../services/keycloak-profile.service";
import {Anonymous, User} from "../../models/user.model";
import {Router} from "@angular/router";
import {CommentsComponent} from "../comments/comments.component";
import {parseKatex} from "../../helpers/parse.helper";
import {TagsComponent} from "../tags/tags.component";
import {CopyComponent} from "../copy/copy.component";
import {MenuComponent} from "../menu/menu.component";

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
    MenuComponent
  ],
  styleUrls: ['./note.component.css'],
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

  constructor(private router: Router, private fb: FormBuilder, private markdownService: MarkdownService, private readonly noteService: NoteService) {
  }

  buildForm(markdownText: string) {
    this.templateForm = this.fb.group({
      body: [markdownText],
      isPreview: [true]
    });
  }

  saveChanges() {
    if (!this.hasUnsavedChangesProp) return;
    if (this.note?.content != this.markdownText)
      this.noteService.saveContentNote({content: this.markdownText, syntax_name: "markdown"}, this.uuid).subscribe({
        next: note => {
          if (this.note != undefined)
            this.note.content = note.content || "";
          this.checkAndChangeVals()
        },
        error: (err) => {
          console.error('Save failed:', err);
        }
      });
    if (this.note?.is_public != this.isPublic)
      this.noteService.publicNote(this.isPublic, this.uuid).subscribe({
        next: note => {
          if (this.note != undefined)
            this.note.is_public = note.is_public;
          this.checkAndChangeVals()
        },
        error: (err) => {
          console.error('Save failed:', err);
        }
      });
    if (this.note?.description !== this.description || this.note?.title !== this.title)
      this.noteService.saveNote(new NoteSaveOther(this.title,
        this.description || this.note?.description || ""), this.uuid).subscribe({
        next: note => {
          if (this.note != undefined) {
            this.note.description = note.description;
            this.note.title = note.title;
          }
          this.checkAndChangeVals()
        },
        error: (err) => {
          console.error('Save failed:', err);
        }
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
