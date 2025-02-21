import {Injectable, OnDestroy} from '@angular/core';
import Keycloak from 'keycloak-js';
import {Anonymous, User} from "../models/user.model";
import {from, map, Observable, ReplaySubject, Subject, takeUntil} from 'rxjs';

@Injectable({providedIn: 'root'})
export class UserService implements OnDestroy {
  private user$ = new ReplaySubject<User | Anonymous>(1);
  private destroyed$ = new Subject<void>();

  constructor(private readonly keycloak: Keycloak) {
    this.updateUserState();
    keycloak.onAuthSuccess = () => this.updateUserState();
    keycloak.onAuthLogout = () => this.user$.next(new Anonymous());
  }

  private updateUserState(): void {
    if (this.keycloak.authenticated) {
      from(this.keycloak.loadUserProfile()).pipe(
        map(profile => new User(profile.firstName ?? "",
          profile.email ?? "",
          profile.username ?? "")),
        takeUntil(this.destroyed$) // Cleanup on service destruction
      ).subscribe({
        next: user => this.user$.next(user),
        error: () => this.user$.next(new Anonymous())
      });
    } else {
      this.user$.next(new Anonymous());
    }
  }

  getUser(): Observable<User | Anonymous> {
    return this.user$.asObservable();
  }

  ngOnDestroy() {
    this.destroyed$.next();
    this.destroyed$.complete();
  }
}
