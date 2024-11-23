import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FilterService {
  private approvalFilter = new BehaviorSubject<boolean | null>(null); // null = pas de filtre
  currentFilter = this.approvalFilter.asObservable();

  setFilter(isApproved: boolean | null): void {
    this.approvalFilter.next(isApproved);
  }
  constructor() { }
}
