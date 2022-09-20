import { Component, AfterViewInit, ViewChild, Input, OnInit } from '@angular/core';
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
export class MfTableComponent implements OnInit, AfterViewInit {

  invested_amount: number = 0;
  current_amount: number = 0;

  @Input()
  holdings: MfHolding[] = [];

  dataSource: MatTableDataSource<MfHolding> = new MatTableDataSource();

  displayedColumns: string[] = ['name', 'code', 'quantity', 'buy_price', 'LTP'];

  constructor(private dataService: DataService) {}

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource<MfHolding>(this.holdings);
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<MfHolding>(this.holdings, $event);
    if (typeof(sortedData) !== 'undefined') this.dataSource = sortedData;
  }
}
