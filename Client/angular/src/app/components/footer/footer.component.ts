import {Component} from '@angular/core';
import {FaIconComponent} from "@fortawesome/angular-fontawesome";
import {faC, faCopyright, faLink} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-footer',
  templateUrl: 'footer.component.html',
  standalone: true,
  imports: [
    FaIconComponent
  ],
  styleUrls: [`footer.component.scss`]
})

export class FooterComponent {

  protected readonly faC = faC;
  protected readonly faCopyright = faCopyright;
  protected readonly faLink = faLink;
}
