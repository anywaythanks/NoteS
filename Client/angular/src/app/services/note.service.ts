import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {filter, map, Observable, switchMap} from 'rxjs';
import {
  CommentRequest,
  CommentSaveContent,
  Note,
  NoteRequest,
  NoteSaveContent,
  NoteSaveOther
} from "../models/note.model";
import {Page} from "../models/page.model";
import {environment} from "../../environments/environment";
import {UserService} from "./keycloak-profile.service";
import {User} from "../models/user.model";
import {Tag, TagName} from "../models/tag.model";
import {initTime, initTimePage} from "../helpers/date.helper";

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private baseUrl = environment.backend.uri;
  private http = inject(HttpClient);
  private userService = inject(UserService);
  private readonly LIMIT = 11;

  getNote(path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Note>(`${this.baseUrl}/public/${user.username}/notes/${path}`);
      }),
      map(initTime));
  }

  getUserTags(): Observable<Tag[]> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Tag[]>(`${this.baseUrl}/public/${user.username}/tags`);
      }));
  }

  saveContentComment(noteData: CommentSaveContent, path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/comments/${path}`,
          noteData
        );
      }),
      map(initTime)
    );
  }

  saveContentNote(noteData: NoteSaveContent, path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/notes/${path}/content`,
          noteData
        );
      }),
      map(initTime)
    );
  }

  createTag(tag: Tag): Observable<any> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post(
          `${this.baseUrl}/public/${user.username}/tags`,
          tag
        );
      })
    );
  }

  addTag(tagName: string, path: string): Observable<any> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post(
          `${this.baseUrl}/public/${user.username}/notes/${path}/tags`, {name: tagName}
        );
      })
    );
  }

  publicNote(isPublic: boolean, path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/notes/${path}/publish`,
          {
            is_public: isPublic,
          }
        );
      }),
      map(initTime)
    );
  }

  deleteNote(path: string): Observable<any> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.delete(
          `${this.baseUrl}/public/${user.username}/notes/${path}`
        );
      })
    );
  }

  deleteComment(path: string): Observable<any> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.delete(
          `${this.baseUrl}/public/${user.username}/comments/${path}`
        );
      })
    );
  }

  deleteTag(tag: string, path: string): Observable<any> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.delete(
          `${this.baseUrl}/public/${user.username}/notes/${path}/tags/${tag}`
        );
      })
    );
  }

  saveNote(noteData: NoteSaveOther, path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/notes/${path}`,
          noteData
        );
      }),
      map(initTime)
    );
  }

  createNote(noteData: NoteRequest): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/notes`,
          noteData
        );
      }),
      map(initTime)
    );
  }

  createComment(noteData: CommentRequest, path: string): Observable<Note> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        if (!user?.username) {
          throw new Error('Username not available');
        }
        return this.http.post<Note>(
          `${this.baseUrl}/public/${user.username}/notes/${path}/comments`,
          noteData
        );
      }),
      map(initTime)
    );
  }

  pageNotes(page: number): Observable<Page<Note>> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Page<Note>>(`${this.baseUrl}/public/${user.username}/notes`, {
          params: {
            Page: page,
            Limit: this.LIMIT
          }
        });
      }),
      map(initTimePage)
    );
  }

  pageNotesByTags(page: number, includeTags: TagName[], excludeTags: TagName[]): Observable<Page<Note>> {
    let params = new HttpParams();
    params = params.append('Page', page);
    params = params.append('Limit', this.LIMIT);
    for (const tag of includeTags) {
      params = params.append("tag", tag.name);
    }
    for (const tag of excludeTags) {
      params = params.append("filter", tag.name);
    }
    console.log(params)
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Page<Note>>(`${this.baseUrl}/public/${user.username}/notes/search/tag`, {
          params: params
        });
      }),
      map(initTimePage)
    );
  }

  pageNotesByTitle(page: number, title: string): Observable<Page<Note>> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Page<Note>>(`${this.baseUrl}/public/${user.username}/notes/search/title`, {
          params: {
            Page: page,
            Title: title,
            Limit: this.LIMIT
          }
        });
      }),
      map(initTimePage)
    );
  }

  pageNotesSemantic(page: number, query: string): Observable<Page<Note>> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Page<Note>>(`${this.baseUrl}/public/${user.username}/notes/search/semantic`, {
          params: {
            Page: page,
            Query: query,
            Limit: this.LIMIT
          }
        });
      }),
      map(initTimePage)
    );
  }

  pageComments(page: number, path: string): Observable<Page<Note>> {
    return this.userService.getUser().pipe(
      filter((user): user is User => 'username' in user),
      switchMap(user => {
        return this.http.get<Page<Note>>(`${this.baseUrl}/public/${user.username}/notes/${path}/comments`, {
          params: {
            Page: page,
            Limit: this.LIMIT
          }
        });
      }),
      map(initTimePage)
    );
  }
}
