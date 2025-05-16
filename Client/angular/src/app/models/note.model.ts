import {Tag} from "./tag.model";

export interface Note {
  path: string;
  title: string;
  note_type: 'COMMENT' | "NOTE" | "COMMENT_REDACTED";
  owner_account_name: string;//TODO: исправить апи// Эм в чем? Я чет забыл
  state: 'ACTIVE_MODIFIED' | "ACTIVE" | "PENDING_CREATE" | "PENDING_MODIFY" | "FAILED";
  content: string;
  description: string;
  created_at: Date;
  createdAtText: string;
  is_public: boolean;
  tags: Tag[];
}

export interface NoteRequest {
  title: string;
  syntax_name: string;
  content: string;
  description: string;
}

export interface CommentRequest {
  title: string;
  syntax_name: string;
  description: string;
  content: string;
}

export class NoteSave {
  syntax_name: string;
  content: string;
  title: string;
  description: string;


  constructor(syntax_name: string, content: string, title: string, description: string) {
    this.syntax_name = syntax_name;
    this.content = content;
    this.title = title;
    this.description = description;
  }
}
export class CommentEdit {
  syntax_name: string;
  content: string;
  title: string;

  constructor(syntax_name: string, content: string, title: string) {
    this.syntax_name = syntax_name;
    this.content = content;
    this.title = title;
  }
}

export interface SearchState {
  query: string;
  searchType: 'semantic' | 'by-title' | 'by-tags' | "nope";
  includedTags: Tag[];
  excludedTags: Tag[];
  availableTags: Tag[];
}
