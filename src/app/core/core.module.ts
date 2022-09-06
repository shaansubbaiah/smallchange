import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavbarComponent } from './navbar/navbar.component';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [NavbarComponent],
  imports: [CommonModule, MatToolbarModule, MatButtonModule],
  exports: [NavbarComponent],
})
export class CoreModule {}
