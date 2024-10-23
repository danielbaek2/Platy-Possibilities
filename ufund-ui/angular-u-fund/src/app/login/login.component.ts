import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { login } from "../login";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {}
  username: = string = "";
  loginForm = FormGroup = this.fb.group({
    username: ['',[Validators.required, Validators.pattern('^[a-zA-Z0-9]+$')]]
    });

  constructor(private fb: FormBuilder) { }
    onSubmit(){
      if(!this.loginForm.valid){
        return;}
        this.username = this.loginForm.value.username!;
        // Checker for user.
        }
}

