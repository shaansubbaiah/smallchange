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

    this.authService.authenticate(
      this.loginDetails.get('username')?.value,
      this.loginDetails.get('password')?.value
    ).subscribe((isLoginSuccessful : boolean) => {
      this.authService.setLoggedIn(isLoginSuccessful);

      if (isLoginSuccessful)
        this.router.navigateByUrl('/home');
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
