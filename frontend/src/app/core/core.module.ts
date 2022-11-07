import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NavbarComponent } from './navbar/navbar.component';
import { FooterComponent } from './footer/footer.component';

import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatMenuModule } from '@angular/material/menu';

import { HttpClientModule } from '@angular/common/http';



@NgModule({
  declarations: [NavbarComponent, FooterComponent],
  imports: [
    CommonModule,
    MatButtonModule,
    MatIconModule,
    RouterModule,
    MatCardModule,
    MatDividerModule,
    MatMenuModule,
    HttpClientModule
  ],
  exports: [NavbarComponent, FooterComponent],
})
export class CoreModule {}
