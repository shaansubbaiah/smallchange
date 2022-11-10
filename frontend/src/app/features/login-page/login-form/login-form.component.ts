import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  FormGroupDirective,
  NgForm,
  Validators,
} from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { Router } from '@angular/router';
import { User } from 'src/app/core/models/user';
import { AlertService } from 'src/app/core/services/alert.service';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit, ErrorStateMatcher {
  loginValid: boolean = true;
  hidePassword: boolean = true;
  isLoading: boolean = false;
  loginDetails: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {
    this.loginDetails = this.fb.group({
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
    this.isLoading = true;
    let username = this.loginDetails.get('username')?.value;

    this.authService
      .authenticate(
        this.loginDetails.get('username')?.value,
        this.loginDetails.get('password')?.value
      )
      .subscribe({
        next: (result) => {
          // console.log('login success');
          let user = new User(
            username,
            result.firstName,
            result.lastName,
            result.email,
            result.lastLoginTimestamp,
            result.jwt
          );

          localStorage.setItem('currentUser', JSON.stringify(user));
          localStorage.setItem('token', result.jwt);

          this.alertService.open({
            type: 'success',
            message: 'Login successfull!',
          });

          this.authService.setLoggedIn(true);
          this.loginValid = true;
          this.isLoading = false;
          this.router.navigateByUrl('/market-place');
        },
        error: (e) => {
          this.alertService.open({ type: 'error', message: e.statusText });
          switch (e.status) {
            case 401:
            case 403:
              this.loginValid = false;
              this.authService.setLoggedIn(false);
              break;
            case 500:
            // console.log('Internal server error!');
          }
          this.isLoading = false;
        },
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
