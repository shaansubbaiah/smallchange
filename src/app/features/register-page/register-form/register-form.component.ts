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
          // Atleast 1 uppercase, 1 lowercase, 1 symbol
          Validators.pattern('^(?=.*[a-z])(?=.*[A-Z])(?=.*[^da-zA-Z]).*$'),
        ],
      ],
    });
  }

  onSubmit() {
    console.log(this.registerDetails.value);
    this.authService.register(
      this.registerDetails.get('username')?.value,
      this.registerDetails.get('email')?.value,
      this.registerDetails.get('password')?.value
    );
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
