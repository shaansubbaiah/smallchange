import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TitleComponent } from './title/title.component';
import { ReusableTableComponent } from './reusable-table/reusable-table.component';
import { ReusableTableOverviewComponent } from './reusable-table-overview/reusable-table-overview.component';
import { InfoDialogComponent } from './info-dialog/info-dialog.component';

import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
  declarations: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
    InfoDialogComponent,
  ],
  imports: [
    CommonModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatButtonModule,
  ],
  exports: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
    InfoDialogComponent,
  ],
})
export class SharedModule {}
