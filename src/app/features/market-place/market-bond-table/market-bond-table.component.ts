import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BondHolding } from '../../../core/models/bond-holding';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-market-bond-table',
  templateUrl: './market-bond-table.component.html',
  styleUrls: ['./market-bond-table.component.scss']
})
export class MarketBondTableComponent implements OnInit, AfterViewInit {

  invested_amount: number = 0;
  current_amount: number = 0;

  @Input()
  holdings: BondHolding[] = [];

  dataSource: MatTableDataSource<BondHolding> = new MatTableDataSource();

  displayedColumns: string[] = [
    'name',
    'code',
    'quantity',
    'buy_price',
    'LTP',
    'asset_class',
  ];


  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }

  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource<BondHolding>(this.holdings);
    this.dataSource.paginator = this.paginator;
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<BondHolding>(this.holdings, $event);
    if (typeof(sortedData) !== 'undefined') this.dataSource = sortedData;
  }

}
