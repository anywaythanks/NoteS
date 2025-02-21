import {Component, inject, Input} from '@angular/core';
import {AngularMarkdownEditorModule} from 'angular-markdown-editor';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FaIconComponent, IconDefinition} from "@fortawesome/angular-fontawesome";
import clipboard from "clipboard-js";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-copy',
  templateUrl: './copy.component.html',
  standalone: true,
  imports: [
    AngularMarkdownEditorModule,
    FormsModule,
    ReactiveFormsModule,
    FaIconComponent
  ],
  styleUrls: ['./copy.component.scss'],
})
export class CopyComponent {
  @Input() data!: string | Element;
  @Input() icon!: IconDefinition;
  private snackBar = inject(MatSnackBar);
  private showFeedback() {
    this.snackBar.open("Copied!", "Ok");
    setTimeout(() => {
      this.snackBar.dismiss();
    }, 2000);
  }

  copyToClipboard() {
    clipboard.copy(this.data)
      .then(() => {
        this.showFeedback();
      })
      .catch(err => {
        console.error('Failed to copy:', err);
      });
  }
}
