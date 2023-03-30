import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { TokenStorageService } from 'src/app/services/token-storage.service';
import { UserStatusService } from 'src/app/services/user-status.service';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {
  form: any = {
    username: null,
    password: null
  }
  isLoggedIn = false;
  loginSuccessMessage: string = 'Successfully logged in!';
  constructor(
    private authService: AuthService,
    private tokenStorage: TokenStorageService,
    private http: HttpClient,
    private router: Router,
    private userStatusService: UserStatusService
  ) {}

  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
    }
  }
  onSubmit() {
    const {username, password} = this.form;
    console.log('submitting')
    this.http.post<LoginPostData>("https://localhost:7248/api/Login/login", {username, password})
      .subscribe(
      data => {
        this.tokenStorage.saveToken(data.id_token);
        this.tokenStorage.saveUser(data.id);
        this.isLoggedIn = true;
        console.log('logged in')
        this.userStatusService.setLoggedIn(true);
        this.authService.currentUser = {
          username : username
        };
        this.router.navigate(['/list-posts']);
        //window.location.reload();
        
        console.log('submitted')
      },
      (error) => {
        this.loginSuccessMessage = 'Login failed.';
        console.error(error);
      }
      )
  }
  
}

export interface LoginPostData {
  id_token: string;
  id: number;
}
