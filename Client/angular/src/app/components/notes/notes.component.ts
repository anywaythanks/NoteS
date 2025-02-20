import {AfterViewInit, Component} from '@angular/core';

import {NoteService} from '../../services/note.service';
import {BehaviorSubject, Observable, switchMap} from "rxjs";
import {AsyncPipe} from "@angular/common";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import {faClock, faEye, faEyeSlash, faPlus, faTag, faUser} from "@fortawesome/free-solid-svg-icons";
import {SomeComponent} from "../some/some.component";
import {PaginationComponent} from "../pagination/pagination.component";
import {ActivatedRoute, Router} from "@angular/router";
import {MenuComponent} from "../menu/menu.component";
import {MatCard, MatCardContent, MatCardFooter, MatCardHeader, MatCardTitle} from "@angular/material/card";
import {Note, SearchState} from "../../models/note.model";
import {Tag} from "../../models/tag.model";
import {Page} from "../../models/page.model";

@Component({
  selector: 'app-notes',
  templateUrl: './notes.component.html',
  standalone: true,
  imports: [
    AsyncPipe, FontAwesomeModule, SomeComponent, PaginationComponent, MenuComponent, MatCard, MatCardContent, MatCardTitle, MatCardFooter, MatCardHeader
  ],
  styleUrls: ['./notes.component.css']
})
export class NotesComponent implements AfterViewInit {
  readonly TagQuantity = 3;
  notesSubj!: BehaviorSubject<number>;
  type: SearchState["searchType"] = 'nope';
  query: string = "";
  tags!: { included: Tag[]; excluded: Tag[] };
  notes!: Observable<Page<Note>>;

  constructor(
    private noteService: NoteService,
    private route: ActivatedRoute,
    private router: Router
  ) {
  }

  ngAfterViewInit(): void {
    this.notesSubj = new BehaviorSubject<number>(1);
    this.notes = this.notesSubj.pipe(switchMap(page => this.searchPage(page)));
    this.syncInitialPage();
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

  updatePage(newPage: number) {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {page: newPage},
      queryParamsHandling: 'merge',
      replaceUrl: true
    }).then(_ => this.notesSubj.next(newPage))
  }

  searchChange(query: string) {
    if (query.length > 0 && (this.type == "by-title" || this.type == "semantic") && this.query !== query) {
      this.query = query;
      this.updatePage(1);
    } else if (query.length == 0 && (this.type == "by-title" || this.type == "semantic")) {
      this.type = "nope";
      this.query = query;
      this.updatePage(1);
    }
  }

  typeChange(type: SearchState["searchType"]) {
    if (this.type != type) {
      this.type = type ?? "nope"
      this.query = ""
    }
  }

  tagsChange(tags: { included: Tag[]; excluded: Tag[] }) {
    this.tags = tags;
    if (this.type == "by-tags") {
      this.updatePage(1);
    }
  }

  searchPage(page: number) {
    switch (this.type) {
      case "nope":
        return this.noteService.pageNotes(page);
      case "by-tags":
        return this.noteService.pageNotesByTags(page, this.tags.included, this.tags.excluded);
      case "by-title":
        return this.noteService.pageNotesByTitle(page, this.query);
      case "semantic":
        return this.noteService.pageNotesSemantic(page, this.query);
    }
  }

  protected readonly faUser = faUser;
  protected readonly faClock = faClock;
  protected readonly faEye = faEye;
  protected readonly faEyeSlash = faEyeSlash;
  protected readonly faTag = faTag;
  protected readonly faPlus = faPlus;
}
