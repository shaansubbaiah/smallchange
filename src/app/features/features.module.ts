import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';

import { LoginPageComponent } from './login-page/login-page.component';
import { LoginFormComponent } from './login-page/login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';

@NgModule({
  declarations: [LoginPageComponent, LoginFormComponent, HomePageComponent],
  imports: [
    CommonModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatCardModule,
    MatButtonModule,
  ],
  exports: [LoginPageComponent, HomePageComponent],
})
export class FeaturesModule {}
