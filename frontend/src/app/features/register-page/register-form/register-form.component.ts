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
import { AlertService } from 'src/app/core/services/alert.service';
import { AuthService } from 'src/app/core/services/auth.service';
@Component({
  selector: 'app-register-form',
  templateUrl: './register-form.component.html',
  styleUrls: ['./register-form.component.scss'],
})
export class RegisterFormComponent implements OnInit, ErrorStateMatcher {
  hidePassword: boolean = true;
  isLoading: boolean = false;
  registerDetails: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private alertService: AlertService,
    private router: Router
  ) {
    this.registerDetails = this.fb.group({
      userId: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.maxLength(10),
        ],
      ],
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(3),
          Validators.pattern("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"),
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
    this.isLoading = true;
    console.log(this.registerDetails.value);
    this.authService
      .register(
        this.registerDetails.get('userId')?.value,
        this.registerDetails.get('name')?.value,
        this.registerDetails.get('email')?.value,
        this.registerDetails.get('password')?.value
      )
      .subscribe({
        next: (result) => {
          // console.log(result);
          this.alertService.open({
            type: 'success',
            message: 'Registration successfull!',
          });
          this.isLoading = false;
          this.router.navigateByUrl('/login');
        },
        error: (e) => {
          // console.log(e);
          this.alertService.open({ type: 'error', message: e.error.title });
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
