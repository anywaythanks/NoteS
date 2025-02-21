import {Component, OnInit} from "@angular/core";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NoteService} from "../../services/note.service";
import {NoteRequest} from "../../models/note.model";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgForOf
  ],
  selector: 'app-note-form',
  templateUrl: './some.component.html',
  styleUrls: ['./some.component.css']
})
export class SomeComponent implements OnInit {
  noteForm: FormGroup;
  syntaxOptions = ['plaintext', 'javascript', 'python', 'html', 'css']; // Add your syntax options
  isLoading = false;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private noteService: NoteService
  ) {
    this.noteForm = this.fb.group({
      title: ['', Validators.required],
      syntax_name: ['plaintext', Validators.required],
      content: ['', Validators.required],
      description: ['']
    });
  }

  ngOnInit(): void {
  }

  onSubmit() {
    if (this.noteForm.invalid) {
      return;
    }

    this.isLoading = true;
    this.errorMessage = null;

    const noteRequest: NoteRequest = this.noteForm.value;

    this.noteService.createNote(noteRequest).subscribe({
      next: _ => {
        this.isLoading = false;
        // Handle successful submission (e.g., reset form, show success message)
        this.noteForm.reset({
          syntax_name: 'plaintext'
        });
      },
      error: (err) => {
        this.isLoading = false;
        this.errorMessage = err.message || 'Failed to create note';
      }
    });
  }
}
