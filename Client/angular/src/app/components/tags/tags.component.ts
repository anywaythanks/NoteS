import {Component, Input, OnInit} from '@angular/core';
import {NoteService} from "../../services/note.service";
import {AngularMarkdownEditorModule} from 'angular-markdown-editor';
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {Tag} from "../../models/tag.model";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {faCirclePlus, faCircleXmark, faMagnifyingGlass, faTag} from "@fortawesome/free-solid-svg-icons";
import {NgForOf} from "@angular/common";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {MatChipListbox, MatChipRemove, MatChipRow} from "@angular/material/chips";
import {MatFormField, MatPrefix} from "@angular/material/form-field";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {MatInput} from "@angular/material/input";

@Component({
  selector: 'app-tags',
  templateUrl: './tags.component.html',
  standalone: true,
  imports: [
    AngularMarkdownEditorModule,
    FormsModule,
    ReactiveFormsModule,
    FaIconComponent,
    NgForOf,
    MatChipListbox,
    MatChipRemove,
    MatChipRow,
    MatPrefix,
    MatAutocomplete,
    MatAutocompleteTrigger,
    MatFormField,
    MatInput,
    MatOption
  ],
  styleUrls: ['./tags.component.css'],
})
export class TagsComponent implements OnInit {
  @Input() path!: string;
  @Input() isEditMode = false;
  @Input() noteTags!: Tag[];
  allUserTags: Tag[] = [];
  filteredTags: Tag[] = [];
  showDropdown = false;
  searchControl = new FormControl();

  constructor(private readonly noteService: NoteService) {
  }

  private loadTags() {
    // Load user's tags
    this.noteService.getUserTags().subscribe(tags => {
      this.allUserTags = tags;
    });
  }

  private setupSearch() {
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(query => {
      if (typeof query !== 'string') return;

      this.filteredTags = this.filterTags(query);
      this.showDropdown = this.filteredTags.length > 0 || !!query;
    });
  }

  ngOnInit() {
    this.loadTags();
    this.setupSearch();
  }

  private filterTags(query: string): Tag[] {
    const normalizedQuery = query.toLowerCase();
    const availableTags = this.allUserTags.filter(userTag =>
      !this.noteTags.some(noteTag => noteTag.name === userTag.name)
    );

    // Filter matching tags
    const matchingTags = availableTags.filter(tag =>
      tag.name.toLowerCase().includes(normalizedQuery)
    );

    // Add "Create new" option if query doesn't match
    if (query && !availableTags.some(t => t.name === query)) {
      matchingTags.push({name: query, color: this.getDefaultColor()});
    }

    return matchingTags;
  }

  handleTagSelect(selectedTag: Tag) {
    const isExistingTag = this.allUserTags.some(t => t.name === selectedTag.name);

    if (isExistingTag) {
      this.addExistingTag(selectedTag);
    } else {
      this.createNewTag(selectedTag);
    }

    this.clearSearch();
  }

  private addExistingTag(tag: Tag) {
    this.noteService.addTag(tag.name, this.path).subscribe({
      next: () => this.noteTags.push(tag),
      error: (err) => console.error('Error adding tag:', err)
    });
  }

  private createNewTag(tag: Tag) {
    this.noteService.createTag(tag).subscribe({
      next: (createdTag) => {
        this.allUserTags.push(createdTag);
        this.addExistingTag(createdTag);
      },
      error: (err) => console.error('Error creating tag:', err)
    });
  }

  private clearSearch() {
    this.searchControl.setValue('');
    this.showDropdown = false;
  }

  private getDefaultColor(): number {
    // Implement your color selection logic
    return 0; // Example default color index
  }

  filter(tag: Tag): boolean {
    return this.allUserTags.some(t => t.name === tag.name);
  }

  deleteTag(tag: string) {
    if (confirm('Are you sure you want to remove this?')) {
      this.noteService.deleteTag(tag, this.path).subscribe(_ => {
        let index = this.noteTags.findIndex(d => d.name === tag);
        this.noteTags.splice(index, 1);
      })
    }
  }

  protected readonly faTag = faTag;
  protected readonly faCircleXmark = faCircleXmark;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;
  protected readonly faCirclePlus = faCirclePlus;
}
