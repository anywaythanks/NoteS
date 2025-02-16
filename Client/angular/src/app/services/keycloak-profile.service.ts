import {Injectable} from '@angular/core';
import Keycloak from 'keycloak-js';
import {User} from "../models/user.model";
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class UserService {
  private user$ = new BehaviorSubject<User | undefined>(undefined);

  constructor(private readonly keycloak: Keycloak) {
  }

  loadProfile(): Observable<User | undefined> {
    if (this.keycloak?.authenticated) {
      this.keycloak.loadUserProfile()
        .then(profile => this.user$.next({
          email: profile.email,
          name: profile.firstName ?? "",
          username: profile.username
        }))
        .catch(console.error);
    }
    return this.user$.asObservable();
  }

  getProfile(): Observable<User | undefined> {
    return this.user$.asObservable();
  }
}
