import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { User } from "../user";
import { UserService } from "../user.service";
import { CurrentUserService} from "../current-user.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements  OnInit {
  messages: string = '';
  loginForm: FormGroup = this.fb.group({
    username: ['',[Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]]
    });
  users: User[] = [];
  currentUser!: User;
  ngOnInit() : void{

  }

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private userService: UserService,
    private currentUserService: CurrentUserService) { }

  login(){
    let username: string = this.loginForm.value.username;

    this.userService.searchUsers(username).subscribe((usernameInfo: any) => {
      this.users = usernameInfo;
      if (this.users && this.users.length === 0) {
        this.messages = "No user found";
      } else {
        for (let i = 0; i < this.users.length; i++) {
          if (this.users[i].username === username) {
            this.currentUser = new User(
              this.users[i].username,
              this.users[i].admin,
            );
            this.userService.loginUser(this.currentUser).subscribe((userData: any) => {
              this.currentUser = userData;
              this.currentUserService.saveCurrentUser(this.currentUser);
              if (this.currentUser.admin) {
                this.router.navigate(['/admin']);
              } else {
                this.router.navigate(['']); // cupboard/helper
              }
            });
          } else {
            this.messages = "No user;"
          }
        }
    }
  });
  }
}
