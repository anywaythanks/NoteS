import {Component} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MenuComponent} from "../menu/menu.component";
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-forbidden',
  imports: [RouterModule, MenuComponent, FooterComponent],
  templateUrl: './forbidden.component.html',
  standalone: true,
  styleUrls: ['./forbidden.component.css']
})
export class ForbiddenComponent {
}
