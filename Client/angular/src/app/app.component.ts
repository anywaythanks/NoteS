import {Component, Injectable} from '@angular/core';
import {RouterModule} from '@angular/router';

import {MenuComponent} from './components/menu/menu.component';
import {UserService} from "./services/keycloak-profile.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MenuComponent, RouterModule],
  template: `
    <app-menu></app-menu>
    <main>
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [``]
})
@Injectable({providedIn: 'root'})
export class AppComponent {
  constructor(userService: UserService) {
    userService.loadProfile();
  }
}
