import {Component, EventEmitter, Input, OnChanges, Output} from '@angular/core';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';
import {Page} from "../../models/page.model";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  imports: [
    NgIf,
    NgForOf
  ],
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent implements OnChanges {
  @Input() pageData!: Page<any>;
  @Output() pageChanged = new EventEmitter<number>();

  pageInput$ = new Subject<number>();
  currentPage: number = 1;

  constructor() {
    this.pageInput$.pipe(
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(page => this.validateAndGoToPage(page));
  }

  ngOnChanges() {
    if (this.pageData) {
      this.currentPage = this.pageData.page;
    }
  }

  get pagesRange(): number[] {
    if (!this.pageData) return [];
    const start = Math.max(1, this.pageData.page - 2);
    const end = Math.min(this.pageData.total_pages, this.pageData.page + 2);
    return Array.from({length: end - start + 1}, (_, i) => start + i);
  }

  nextPage() {
    if (this.pageData.page < this.pageData.total_pages) {
      this.goToPage(this.pageData.page + 1);
    }
  }

  previousPage() {
    if (this.pageData.page > 1) {
      this.goToPage(this.pageData.page - 1);
    }
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.pageData.total_pages) {
      this.pageChanged.emit(page);
    }
  }

  getValue(event: Event): number {
    return parseInt((event.target as HTMLInputElement).value);
  }

  validateAndGoToPage(page: number | string) {
    const numericPage = Number(page);
    if (!isNaN(numericPage)) {
      this.goToPage(numericPage);
    }
  }

  protected readonly console = console;
}
