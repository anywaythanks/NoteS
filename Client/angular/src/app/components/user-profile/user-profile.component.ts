import {Component, OnInit} from '@angular/core';
import {User} from '../../models/user.model';
import {UserService} from "../../services/keycloak-profile.service";
import {MenuComponent} from "../menu/menu.component";
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-user-profile',
  templateUrl: 'user-profile.component.html',
  standalone: true,
  imports: [
    MenuComponent,
    FooterComponent
  ],
  styleUrls: [`user-profile.component.css`]
})

export class UserProfileComponent implements OnInit {
  user: User | undefined;

  constructor(private readonly userService: UserService) {
  }

  async ngOnInit() {
    this.userService.getUser().subscribe(
      u => {
        if (u.type === 'user') this.user = u
      });
  }
}
