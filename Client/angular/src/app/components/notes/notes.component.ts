import { Component, inject, OnInit } from '@angular/core';

import { NoteService } from '../../services/note.service';
import { Note } from '../../models/note.model';

@Component({
  selector: 'app-books',
  templateUrl: './notes.component.html',
  standalone: true,
  styleUrls: ['./notes.component.css']
})
export class NotesComponent implements OnInit {
  notes: Note[] = [];
  private readonly noteService = inject(NoteService);

  ngOnInit() {
    this.noteService.pageNotes().subscribe((data) => {
      this.notes = data.items;
    });
  }
}
