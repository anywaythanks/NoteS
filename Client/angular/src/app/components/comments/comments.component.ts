﻿import {Component, Input, OnInit} from '@angular/core';
import {NoteService} from "../../services/note.service";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {timeAgo} from "../../helpers/date.helper";
import {faClock, faReply, faTrash, faUser} from "@fortawesome/free-solid-svg-icons";
import {AngularMarkdownEditorModule, EditorOption} from 'angular-markdown-editor';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MarkdownComponent, MarkdownService} from "ngx-markdown";
import {ActivatedRoute, Router} from "@angular/router";
import {parseKatex} from "../../helpers/parse.helper";
import {PaginationComponent} from "../pagination/pagination.component";
import {BehaviorSubject, switchMap} from "rxjs";
import {AsyncPipe} from "@angular/common";
import {debounceTime} from "rxjs/operators";
import {User} from "../../models/user.model";

@Component({
  selector: 'app-comments',
  templateUrl: './comments.component.html',
  standalone: true,
  imports: [
    FaIconComponent,
    AngularMarkdownEditorModule,
    FormsModule,
    MarkdownComponent,
    ReactiveFormsModule,
    PaginationComponent,
    AsyncPipe
  ],
  styleUrls: ['./comments.component.css'],
})
export class CommentsComponent implements OnInit {
  @Input() uuid!: string;
  @Input() isOwner = false;
  editorOptions!: EditorOption;
  commentText = '';
  templateForm!: FormGroup;
  notesSubj = new BehaviorSubject<number>(1);
  comments = this.notesSubj.pipe(
    switchMap(page => this.noteService.pageComments(page, this.uuid))
  );

  constructor(private route: ActivatedRoute,
              private router: Router,
              private fb: FormBuilder,
              private markdownService: MarkdownService,
              private readonly noteService: NoteService) {
  }

  buildForm(markdownText: string) {
    this.templateForm = this.fb.group({
      body: [markdownText],
      isPreview: [true]
    });
  }


  private getValidPage(page: string | null): number {
    const parsed = Number(page);
    return Number.isInteger(parsed) && parsed > 0 ? parsed : 1;
  }

  private syncInitialPage() {
    const initialPage = this.getValidPage(
      this.route.snapshot.queryParamMap.get('page')
    );
    this.notesSubj.next(initialPage);
  }

  private syncPage() {
    const currPage = this.getValidPage(
      this.route.snapshot.queryParamMap.get('page')
    );
    this.updatePage(currPage);
  }

  updatePage(newPage: number) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {page: newPage},
      queryParamsHandling: 'merge',
      replaceUrl: true
    }).then(_ => this.notesSubj.next(newPage))
  }

  createComment(set: (_: string) => void) {
    this.noteService.createComment({
      title: `Комментарий к ${this.uuid}`,
      description: "",
      content: this.commentText,
      syntax_name: "markdown"
    }, this.uuid).pipe(debounceTime(700)).subscribe({
      next: _ => {
        this.syncPage();
        set("");
      },
      error: (err) => {
        console.error('Save failed:', err);
      }
    });
  }

  ngOnInit() {
    this.editorOptions = {
      autofocus: true,
      iconlibrary: 'fa',
      savable: false,
      additionalButtons: [[{
        name: 'createMisc',
        data: [{
          name: 'cmdCreate',
          toggle: false,
          title: 'Create',
          icon: {
            fa: 'fa fa-reply'
          },
          callback: (e: any) => {
            this.createComment(s => e.setContent(s));
          }
        }]
      }]],
      parser: () => this.parse(this.commentText),
      onFullscreenExit: () => this.hidePreview(),
    };
    this.buildForm(this.commentText);
    this.syncInitialPage();
  }

  hidePreview() {
  }

  deleteComment(path: string) {//TODO:Доделайте
    if (confirm('Are you sure you want to delete this?')) {
      this.noteService.deleteComment(path).subscribe(_=>this.syncPage());
    }
  }

  hideComment(path: string) {
    if (confirm('Are you sure you want to hide this?')) {
      this.noteService.publicNote(false, path).subscribe(_=>this.syncPage());
    }
  }

  parse(inputValue: string) {
    console.log(this.commentText)
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

  protected readonly timeAgo = timeAgo;
  protected readonly faUser = faUser;
  protected readonly faClock = faClock;
  protected readonly faReply = faReply;
  protected readonly console = console;
  protected readonly faTrash = faTrash;
}
