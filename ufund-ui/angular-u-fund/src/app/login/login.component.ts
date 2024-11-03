import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  message: string = '';

  constructor(private userService: UserService, private router: Router) {}

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
