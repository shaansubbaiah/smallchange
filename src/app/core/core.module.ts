import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { FooterComponent } from './footer/footer.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [NavbarComponent, FooterComponent],
  imports: [CommonModule, MatToolbarModule, MatButtonModule, RouterModule],
  exports: [NavbarComponent],
})
export class CoreModule {}
