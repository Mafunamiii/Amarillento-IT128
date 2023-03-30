import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserStatusService {
  private loggedInSubject = new BehaviorSubject<boolean>(false);
  public loggedIn$ = this.loggedInSubject.asObservable();
  isLoggedIn$: any;

  setLoggedIn(value: boolean) {
    this.loggedInSubject.next(value);
  }

  checkLog(value: boolean) {
    this.loggedInSubject.next(value);
  }
}
