import {Routes} from '@angular/router';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {NotesComponent} from './components/notes/notes.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ForbiddenComponent} from './components/forbidden/forbidden.component';
import {canActivateAuthRole} from './guards/auth-role.guard';
import {canActivateAuthorize} from "./guards/authorize.guard";
import {NoteComponent} from "./components/note/note.component";

export const routes: Routes = [
  {
    path: '',
    title: "NoteS",
    component: NotesComponent,
    canActivate: [canActivateAuthorize, canActivateAuthRole],
    data: {role: 'read-notes'}
  },
  {
    path: 'profile',
    component: UserProfileComponent,
    canActivate: [canActivateAuthRole],
    data: {role: 'view-profile'}
  },
  {
    path: 'note/:uuid',
    component: NoteComponent,
    canActivate: [canActivateAuthorize, canActivateAuthRole],
    canDeactivate: [(component: NoteComponent) => !component.hasUnsavedChangesProp],
    data: {role: 'read-notes'}
  },
  {path: 'forbidden', component: ForbiddenComponent},
  {path: 'notfound', component: NotFoundComponent},
  {path: '**', component: NotFoundComponent}
];
