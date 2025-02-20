import {Component, Input} from '@angular/core';
import {AngularMarkdownEditorModule} from 'angular-markdown-editor';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {FaIconComponent, IconDefinition} from "@fortawesome/angular-fontawesome";
import {NgIf} from "@angular/common";
import clipboard from "clipboard-js";

@Component({
  selector: 'app-copy',
  templateUrl: './copy.component.html',
  standalone: true,
  imports: [
    AngularMarkdownEditorModule,
    FormsModule,
    ReactiveFormsModule,
    FaIconComponent,
    NgIf
  ],
  styleUrls: ['./copy.component.css'],
})
export class CopyComponent {
  @Input() data!: string | Element;
  @Input() icon!: IconDefinition;
  showCopiedAlert = false;

  private showFeedback() {
    this.showCopiedAlert = true;
    setTimeout(() => {
      this.showCopiedAlert = false;
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
