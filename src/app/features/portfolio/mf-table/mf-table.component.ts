import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MfHolding } from '../../../core/models/mf-holding';

@Component({
  selector: 'app-mf-table',
  templateUrl: './mf-table.component.html',
  styleUrls: ['./mf-table.component.scss'],
})
export class MfTableComponent implements AfterViewInit {
  mfs: MfHolding[] = [];
  dataSource!: MatTableDataSource<MfHolding>;

  displayedColumns: string[] = [
    'name',
    'code',
    'buy_price',
    'LTP',
    'asset_class',
  ];

  constructor(private dataService: DataService) {}

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.mfs = this.dataService.getMFHolding();
    this.dataSource = new MatTableDataSource<MfHolding>(this.mfs);
    this.dataSource.paginator = this.paginator;
  }
}
