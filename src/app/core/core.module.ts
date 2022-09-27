import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { FooterComponent } from './footer/footer.component';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { AvatarButtonComponent } from './navbar/avatar-button/avatar-button.component';

@NgModule({
  declarations: [NavbarComponent, FooterComponent, AvatarButtonComponent],
  imports: [CommonModule, MatToolbarModule, MatButtonModule, MatIconModule, RouterModule],
  exports: [NavbarComponent, FooterComponent],
})
export class CoreModule {}
