import { Component } from '@angular/core';
import { AuthService } from './services/auth.service';
import { UserStatusService } from './services/user-status.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  isLoggedIn = false

  constructor(
    public authService: AuthService,
    private UserStatusService: UserStatusService
  ){}

  ngOnInit(): void {
    this.UserStatusService.loggedIn$.subscribe((loggedIn) => {
      this.isLoggedIn = loggedIn;
      console.log("isLoggedIn$ value: ", loggedIn);
    });
  }
}
