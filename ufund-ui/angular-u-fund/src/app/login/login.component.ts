import { Component,inject } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';
import { CurrentUserService } from '../current-user.service';

/**
 * Login component that handles functionality for logging in
 */
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

  /**
   * Searches for the entered username and navigates to the appropriate page based on user type
   * Displays message if user is not found
   */
  login(): void {
    this.userService.searchUsers(this.username).subscribe(
      (users) => {
        if (users.length === 0) {
          this.message = 'User not found.'+users;
        } else {
          const user = new User(this.username);
          this.currentUserService.updateCurrentUser(user); // to set the current user.
          if (user.username == 'Admin' || user.username == 'admin'){
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
