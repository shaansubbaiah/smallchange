import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MfHolding } from '../../../core/models/mf-holding';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-mf-table',
  templateUrl: './mf-table.component.html',
  styleUrls: ['./mf-table.component.scss'],
})
export class MfTableComponent implements AfterViewInit {

  invested_amount: number = 0;
  current_amount: number = 0;
  mfs: MfHolding[] = [];
  dataSource!: MatTableDataSource<MfHolding>;

  displayedColumns: string[] = ['name', 'code', 'quantity', 'buy_price', 'LTP'];

  constructor(private dataService: DataService) {}

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.mfs = this.dataService.getMFHolding();
    this.dataSource = new MatTableDataSource<MfHolding>(this.mfs);
    this.dataSource.paginator = this.paginator;
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.mfs.length; i++) {
      this.invested_amount +=
        this.mfs[i].buy_price * this.mfs[i].quantity;
      this.current_amount += this.mfs[i].LTP * this.mfs[i].quantity;
    }
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<MfHolding>(this.mfs, $event);
    if (typeof(sortedData) !== 'undefined') this.dataSource = sortedData;
  }
}
