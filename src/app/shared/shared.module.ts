import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TitleComponent } from './title/title.component';
import { ReusableTableComponent } from './reusable-table/reusable-table.component';

import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { ReusableTableOverviewComponent } from './reusable-table-overview/reusable-table-overview.component';

@NgModule({
  declarations: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
  ],
  imports: [
    CommonModule,
    MatIconModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
  ],
  exports: [
    TitleComponent,
    ReusableTableComponent,
    ReusableTableOverviewComponent,
  ],
})
export class SharedModule {}
