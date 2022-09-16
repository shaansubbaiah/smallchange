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
import { AuthService } from 'src/app/core/services/auth.service';
@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent implements OnInit, ErrorStateMatcher {
  registerValid: boolean = true;
  hidePassword: boolean = true;

  registerDetails: FormGroup;

  constructor(fb: FormBuilder, private authService: AuthService) {
    this.registerDetails = fb.group({
      username: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10),
        ],
      ],
      email: ['', [Validators.required, Validators.email]],
      password: [
        '',
        [
          Validators.required,
          Validators.minLength(8),
          Validators.maxLength(15),
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[^da-zA-Z]).*$'), // Atleast 1 uppercase, 1 lowercase, 1 symbol
        ],
      ],
    });
  }

  authenticate(): void {
    this.registerValid = this.authService.authenticate(
      this.registerDetails.get('username')?.value,
      this.registerDetails.get('password')?.value
    );
  }

  get username() {
    return this.registerDetails.get('username');
  }

  get password() {
    return this.registerDetails.get('password');
  }

  onSubmit() {
    this.authenticate();
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
