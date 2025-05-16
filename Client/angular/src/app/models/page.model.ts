export interface Page<T> {
  items: T[];
  page: number;
  total_pages: number;
  total_elements: number;
}
