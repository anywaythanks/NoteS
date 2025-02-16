import {inject, Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Note} from "../models/note.model";
import {Page} from "../models/page.model";

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private apiUrl = 'http://localhost:5267/api/public/test2/notes';
  private http = inject(HttpClient);

  pageNotes(): Observable<Page<Note>> {
    const r = this.http.get<Page<Note>>(this.apiUrl);
    return r;
  }
}
