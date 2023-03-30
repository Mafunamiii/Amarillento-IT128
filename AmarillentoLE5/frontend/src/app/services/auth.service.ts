import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { UserStatusService } from './user-status.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public redirectUrl: string = "";
  public currentUser: any = null;
  isLoggedIn$: Observable<boolean>;

  constructor(
    private http: HttpClient,
    private userStatusService: UserStatusService,
  ) {
    this.isLoggedIn$ = this.userStatusService.isLoggedIn$;
  }

  login(username: string, password: string): Observable<{ token: string, user: any }> {
    return this.http.post<{ token: string, user: any }>("https://localhost:7248/api/Login/login", {username, password}).pipe(
      map((response: any) => {
        const loginData = {
          token: response.token,
          user: response.user
        };
        this.currentUser = {
          userName: response.user.userName,
          ...response.user
        };
        this.userStatusService.setLoggedIn(true);
        return loginData;
      })
    );
  }

  logout() {
    this.currentUser = null;
    this.userStatusService.setLoggedIn(false);
  }
}
