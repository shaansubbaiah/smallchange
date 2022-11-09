import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { TitleComponent } from './title/title.component';
import { ReusableTableComponent } from './reusable-table/reusable-table.component';
import { ReusableTableOverviewComponent } from './reusable-table-overview/reusable-table-overview.component';
import { InfoDialogComponent } from './info-dialog/info-dialog.component';
import { SnackbarComponent } from './snackbar/snackbar.component';

import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule } from '@angular/material/input';
import { MatDividerModule } from '@angular/material/divider';
import { MatTabsModule } from '@angular/material/tabs';
import { MatButtonToggleModule } from '@angular/material/button-toggle';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { SnakeToTitlePipe } from '../core/pipes/snake-to-title.pipe';
import { RefreshButtonComponent } from './refresh-button/refresh-button.component';

@NgModule({
  declarations: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
    InfoDialogComponent,
    SnackbarComponent,
    SnakeToTitlePipe,
    RefreshButtonComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatButtonModule,
    MatInputModule,
    MatDividerModule,
    MatTabsModule,
    MatButtonToggleModule,
    MatSnackBarModule,
  ],
  exports: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
    InfoDialogComponent,
    SnackbarComponent,
    RefreshButtonComponent,
  ],
})
export class SharedModule {}
