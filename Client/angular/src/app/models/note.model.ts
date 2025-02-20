import {Tag} from "./tag.model";

export interface Note {
  path: string;
  title: string;
  owner_account_name: string;//TODO: исправить апи// Эм в чем? Я чет забыл
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

export class NoteSaveContent {
  syntax_name: string;
  content: string;

  constructor(syntax_name: string, content: string) {
    this.syntax_name = syntax_name;
    this.content = content;
  }
}

export class NoteSaveOther {
  title: string;
  description: string;

  constructor(title: string, description: string) {
    this.title = title;
    this.description = description;
  }
}

export interface SearchState {
  query: string;
  searchType: 'semantic' | 'by-title' | 'by-tags' | "nope";
  includedTags: Tag[];
  excludedTags: Tag[];
  availableTags: Tag[];
}
