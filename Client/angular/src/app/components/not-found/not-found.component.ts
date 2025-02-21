import {Component} from '@angular/core';
import {RouterModule} from '@angular/router';
import {MenuComponent} from "../menu/menu.component";
import {FooterComponent} from "../footer/footer.component";

@Component({
  selector: 'app-not-found',
  imports: [RouterModule, MenuComponent, FooterComponent],
  templateUrl: './not-found.component.html',
  standalone: true,
  styleUrls: ['not-found.component.css']
})
export class NotFoundComponent {
}
