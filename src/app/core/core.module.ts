import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { FooterComponent } from './footer/footer.component';
import { RouterModule } from '@angular/router';
import { MatIconModule } from '@angular/material/icon';
import { AvatarButtonComponent } from './navbar/avatar-button/avatar-button.component';
import { UserMenuActionsComponent } from './navbar/user-menu-actions/user-menu-actions.component';
import { MatCardModule } from "@angular/material/card";
@NgModule({
  declarations: [NavbarComponent, FooterComponent, AvatarButtonComponent, UserMenuActionsComponent],
  imports: [CommonModule, MatToolbarModule, MatButtonModule, MatIconModule, RouterModule, MatCardModule],
  exports: [NavbarComponent, FooterComponent, UserMenuActionsComponent],
})
export class CoreModule {}
