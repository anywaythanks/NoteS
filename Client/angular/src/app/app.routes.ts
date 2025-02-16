import {Routes} from '@angular/router';

import {HomeComponent} from './components/home/home.component';
import {UserProfileComponent} from './components/user-profile/user-profile.component';
import {NotesComponent} from './components/notes/notes.component';
import {NotFoundComponent} from './components/not-found/not-found.component';
import {ForbiddenComponent} from './components/forbidden/forbidden.component';
import {canActivateAuthRole} from './guards/auth-role.guard';
import {canActivateAuthorize} from "./guards/authorize.guard";

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {
    path: 'notes',
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
  {path: 'forbidden', component: ForbiddenComponent},
  {path: '**', component: NotFoundComponent}
];
