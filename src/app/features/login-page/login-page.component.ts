import { Component, OnInit } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroupDirective, NgForm, Validators } from '@angular/forms';
import { ErrorStateMatcher } from '@angular/material/core';
import { LoginAuthService } from "src/app/core/services/login-auth.service";

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit, ErrorStateMatcher {
loginValid: boolean = true;
hidePassword: boolean = true;
authenticate(): void {
  this.loginValid = this.authService.authenticate(this.loginDetails.get('username')?.value, this.loginDetails.get('password')?.value);
}

loginDetails;

  constructor(fb: FormBuilder, private authService: LoginAuthService) {
    this.loginDetails = fb.group({
      username: ['', [Validators.required, Validators.maxLength(10)]],
      password: ['', [Validators.required, Validators.maxLength(15)]]
    });
  }

  get username() {
    return this.loginDetails.get('username');
  }

  get password() {
    return this.loginDetails.get('password');
  }

  onSubmit() {
    this.authenticate();
  }

  isErrorState(control: AbstractControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
  ngOnInit(): void {

  }
}
