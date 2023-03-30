import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment.development';

@Injectable({
  providedIn: 'root'
})
export class TokenStorageService {

  constructor() { }

  signOut(): void {
    window.sessionStorage.clear();
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(environment.tokenKey);
    window.sessionStorage.setItem(environment.tokenKey, token);
  }

  public getToken(): string | null {
    return window.sessionStorage.getItem(environment.tokenKey);
  }

  public saveUser(id : number): void {
    let strId = (id ?? 0).toString();
    window.sessionStorage.removeItem(environment.userKey);
    window.sessionStorage.setItem(environment.userKey, strId);
  }
}
