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
import { CommonUtils } from 'src/app/utils';
@Component({
  selector: 'app-add-account-form',
  templateUrl: './add-account-form.component.html',
  styleUrls: ['./add-account-form.component.scss'],
})
export class AddAccountFormComponent implements OnInit, ErrorStateMatcher {
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
      accNo: ['', [Validators.required, Validators.pattern('^\\d{10}$')]],
      bankName: ['', [Validators.required]],
      accType: ['', [Validators.required]],
    });
  }

  onSubmit() {
    this.isLoading = true;
    console.log(this.registerDetails.value);
    this.authService
    .createBankAccount(
      CommonUtils.getUserDetail('userName') + '',
      this.registerDetails.get('accNo')?.value,
      this.registerDetails.get('bankName')?.value,
      this.registerDetails.get('accType')?.value
    )
    .subscribe({
      next: () => {
        this.alertService.open({
          type: 'success',
          message: 'Creating Account successfull!',
        });
        this.isLoading = false;
        this.router.navigateByUrl('/portfolio');
      },
      error: (e) => {
        this.alertService.open({
          type: 'error',
          message: e.error.title,
        });
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
