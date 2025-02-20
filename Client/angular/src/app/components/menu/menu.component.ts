import {Component, effect, EventEmitter, inject, Injectable, Input, Output} from '@angular/core';
import {RouterModule} from '@angular/router';
import Keycloak from 'keycloak-js';
import {KEYCLOAK_EVENT_SIGNAL, KeycloakEventType, ReadyArgs, typeEventArgs} from 'keycloak-angular';
import {UserService} from "../../services/keycloak-profile.service";
import {toSignal} from "@angular/core/rxjs-interop";
import {Tag} from "../../models/tag.model";
import {
  faCircleMinus,
  faCirclePlus,
  faCircleXmark,
  faMagnifyingGlass,
  faTags,
  faWandMagicSparkles
} from "@fortawesome/free-solid-svg-icons";
import {FormControl, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {NoteService} from "../../services/note.service";
import {AsyncPipe, NgForOf} from "@angular/common";
import {map, startWith} from "rxjs";
import {debounceTime, distinctUntilChanged} from "rxjs/operators";
import {ComponentStore} from "@ngrx/component-store";
import {SearchState} from "../../models/note.model";
import {MatFormField, MatFormFieldModule, MatPrefix, MatSuffix} from "@angular/material/form-field";
import {MatAutocomplete, MatAutocompleteTrigger, MatOption} from "@angular/material/autocomplete";
import {MatChipListbox, MatChipRemove, MatChipRow} from "@angular/material/chips";
import {MatInput, MatInputModule} from "@angular/material/input";
import {MatIconButton} from "@angular/material/button";
import {toColor} from "../../helpers/color.helper";
import {FaIconComponent} from "@fortawesome/angular-fontawesome";

@Component({
  selector: 'app-menu',
  imports: [RouterModule,
    ReactiveFormsModule,
    FormsModule,
    NgForOf,
    MatFormFieldModule,
    MatInputModule,
    MatFormField,
    MatAutocomplete,
    MatOption,
    FaIconComponent,
    MatInput,
    MatAutocompleteTrigger,
    AsyncPipe,
    MatIconButton, MatChipListbox, MatChipRow, MatChipRemove, MatSuffix, MatPrefix],
  templateUrl: './menu.component.html',
  standalone: true,
  styleUrls: ['./menu.component.css']
})
@Injectable({providedIn: 'root'})
export class MenuComponent extends ComponentStore<SearchState> {
  @Input() isSearch = false;
  isSemantic: boolean = false;
  isTags: boolean = false;
  authenticated = false;
  keycloakStatus: string | undefined;
  private readonly keycloak = inject(Keycloak);
  private readonly userService: UserService = inject(UserService);
  private readonly noteService: NoteService = inject(NoteService);
  private readonly keycloakSignal = inject(KEYCLOAK_EVENT_SIGNAL);
  user = toSignal(this.userService.getUser());

  searchControl = new FormControl('');
  tagFilterControl = new FormControl('');

  // Inputs
  @Input() set searchType(type: SearchState['searchType']) {
    this.patchState({searchType: type});
  }

  @Input() set tags(tags: Tag[]) {
    this.patchState({availableTags: tags});
  }

  // Outputs
  @Output() searchChange = new EventEmitter<string>();
  @Output() typeChange = new EventEmitter<SearchState['searchType']>();
  @Output() tagsChange = new EventEmitter<{ included: Tag[]; excluded: Tag[] }>();

  // Observable state
  filteredTags$ = this.tagFilterControl.valueChanges.pipe(
    startWith(''),
    map(value => this._filterTags(value || ''))
  );

  constructor() {
    super({
      query: '',
      searchType: 'nope',
      includedTags: [],
      excludedTags: [],
      availableTags: []
    })
    effect(() => {
      const keycloakEvent = this.keycloakSignal();
      this.keycloakStatus = keycloakEvent.type;
      if (keycloakEvent.type === KeycloakEventType.Ready) {
        this.authenticated = typeEventArgs<ReadyArgs>(keycloakEvent.args);
      }
      if (keycloakEvent.type === KeycloakEventType.AuthLogout) {
        this.authenticated = false;
      }
    });
    this.setupReactiveControls();
    this.loadTags();
  }

  private setupReactiveControls() {
    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(query => {
      let newQuery = query ?? undefined;
      this.patchState({query: newQuery});
      this.calcType();
    });

    this.select(state => state.searchType).subscribe(type => {
      this.typeChange.emit(type);
    });

    this.select(state => ({
      type: state.searchType,
      included: state.includedTags,
      excluded: state.excludedTags
    })).subscribe(tags => {
      if (tags.type == 'by-tags')
        this.tagsChange.emit(tags);
    });
  }

  private loadTags() {
    this.effect(() => this.noteService.getUserTags().pipe(
      map(tags => this.patchState({availableTags: tags}))
    ));
  }

  search(_: SubmitEvent) {
    this.searchChange.emit(this.get().query);
  }

  private _filterTags(value: string): Tag[] {
    const filterValue = value.toLowerCase();
    return this.get(s => s.availableTags).filter(tag =>
      tag.name.toLowerCase().includes(filterValue)
    );
  }

  addTag(tag: Tag, list: 'included' | 'excluded') {
    this.patchState(state => ({
      [list === 'included' ? 'includedTags' : 'excludedTags']: [
        ...state[list === 'included' ? 'includedTags' : 'excludedTags'],
        tag
      ],
      availableTags: state.availableTags.filter(t => t !== tag)
    }));
  }

  removeTag(tag: Tag, list: 'included' | 'excluded') {
    this.patchState(state => ({
      [list === 'included' ? 'includedTags' : 'excludedTags']:
        state[list === 'included' ? 'includedTags' : 'excludedTags']
          .filter(t => t !== tag),
      availableTags: [...state.availableTags, tag]
    }));
  }

  clearVal() {
    this.tagFilterControl.setValue('');
  }

  login() {
    this.keycloak.login();
  }

  logout() {
    this.keycloak.logout();
  }

  toggleSemantic() {
    this.isTags = false;
    this.calcType();
  }

  toggleTags() {
    this.isSemantic = false;
    this.calcType();
  }

  private calcType() {

    this.patchState(st => {
      if (this.isSemantic) {
        st.searchType = 'semantic';
      } else {
        st.searchType = 'by-title';
      }
      if (this.isTags) {
        st.searchType = 'by-tags';
      }
      return st;
    });
    this.typeChange.emit(this.get().searchType);
  }

  protected readonly faWandMagicSparkles = faWandMagicSparkles;
  protected readonly faMagnifyingGlass = faMagnifyingGlass;
  protected readonly faTags = faTags;
  protected readonly toColor = toColor;
  protected readonly faCirclePlus = faCirclePlus;
  protected readonly faCircleMinus = faCircleMinus;
  protected readonly faCircleXmark = faCircleXmark;
}
