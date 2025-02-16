import {Component, OnInit} from '@angular/core';
import {User} from '../../models/user.model';
import Keycloak from 'keycloak-js';
import {UserService} from "../../services/keycloak-profile.service";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  standalone: true,
  styleUrls: [`user-profile.component.css`]
})

export class UserProfileComponent implements OnInit {
  user: User | undefined;

  constructor(private readonly userService: UserService) {
  }

  async ngOnInit() {
    this.userService.getProfile().subscribe(u => this.user = u);
  }
}
