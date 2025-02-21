import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MarkdownModule, MARKED_OPTIONS} from 'ngx-markdown';
import {AngularMarkdownEditorModule} from 'angular-markdown-editor';

import {AppComponent} from './app.component';
import {MenuComponent} from "./components/menu/menu.component";
import {RouterModule} from "@angular/router";
import {appConfig} from "./app.config";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";

// @dynamic
@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    MarkdownModule.forRoot({
      // loader: HttpClient, // optional, only if you use [src] attribute
      markedOptions: {
        provide: MARKED_OPTIONS,
        useValue: {
          gfm: true,
          breaks: false,
          pedantic: false,
        },
      },
    }),
    ReactiveFormsModule,
    AngularMarkdownEditorModule.forRoot({
      iconlibrary: 'fa'
    }),
    BrowserModule,
    BrowserAnimationsModule,
    RouterModule,
    MenuComponent
  ], providers: appConfig.providers,
  bootstrap: [AppComponent]
})
export class AppModule {
}
