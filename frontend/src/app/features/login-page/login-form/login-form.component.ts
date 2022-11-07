import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormControl,
  FormGroup,
  FormGroupDirective,
  NgForm,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { User } from 'src/app/core/models/user';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit, ErrorStateMatcher {
  loginValid: boolean = true;
  hidePassword: boolean = true;

  loginDetails: FormGroup;

  constructor(
    fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginDetails = fb.group({
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10),
        ],
      ],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(15),
        ],
      ],
    });
  }

  get username() {
    return this.loginDetails.get('username');
  }

  get password() {
    return this.loginDetails.get('password');
  }

  onSubmit() {
    console.log(this.loginDetails.value);
    let username = this.loginDetails.get('username')?.value;

    this.authService.authenticate(
      this.loginDetails.get('username')?.value,
      this.loginDetails.get('password')?.value
    ).subscribe((result : any) => {
      const jwt = result.jwt;
      console.log('login success');
      let user = new User(username, result.firstName, result.lastName, result.email, result.lastLoginTimestamp, jwt);
      localStorage.setItem('currentUser', JSON.stringify(user));
      localStorage.setItem('token', jwt);
      this.authService.setLoggedIn(true);
      this.loginValid = true;
      this.router.navigateByUrl('/home');
    }, (e: HttpErrorResponse) => {
      switch(e.status) {
        case 401:
        case 403:
          this.loginValid = false;
          this.authService.setLoggedIn(false);
          break;
        case 500:
          console.log('Internal server error!');
      }
    });
  }

  isErrorState(
    control: AbstractControl | null,
    form: FormGroupDirective | NgForm | null
  ): boolean {
    const isSubmitted = form && form.submitted;
    return !!(
      control &&
      control.invalid &&
      (control.dirty || control.touched || isSubmitted)
    );
  }
  ngOnInit(): void {}
}
