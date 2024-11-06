import { Component,inject } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  message: string = '';

  currentUserService = inject(CurrentUserService);

  constructor(private userService: UserService, private router: Router) {
    this.currentUserService.onButtonClick.subscribe(()=>{
      this.login();
    });
  }

  login(): void {
    /*if (this.username.trim().toLowerCase() === 'admin') {
      this.router.navigate(['/admin']);
      return;
    }*/
    this.userService.searchUsers(this.username).subscribe(
      (users) => {
        if (users.length === 0) {
          this.message = 'No username: '+users;
        } else {
          const user = new User(this.username);
          this.currentUserService.updateCurrentUser(user); // to set the current user.
          if (user.username == 'Admin'){
            this.router.navigate(['/admin']);
          }else{
            this.router.navigate(['/helper']);
          }
        }
      },
      (error) => {console.error('Error during user search:', error);
      }
    );
  }
}
