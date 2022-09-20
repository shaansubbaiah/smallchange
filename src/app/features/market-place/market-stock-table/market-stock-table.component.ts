import { AfterViewInit, Component, Input, OnInit, ViewChild } from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { StockHolding } from 'src/app/core/models/stock-holding';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from "src/app/utils";
@Component({
  selector: 'app-market-stock-table',
  templateUrl: './market-stock-table.component.html',
  styleUrls: ['./market-stock-table.component.scss']
})
export class MarketStockTableComponent implements OnInit, AfterViewInit  {

  dataSource: MatTableDataSource<StockHolding> = new MatTableDataSource();

  invested_amount: number = 0;
  current_amount: number = 0;

  displayedColumns: string[] = [
    'name',
    'code',
    'buy_price',
    'LTP',
    'asset_class',
  ];

  constructor() { }

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;

    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  @Input()
  holdings! : StockHolding[];

  ngAfterViewInit() {

    this.dataSource = new MatTableDataSource<StockHolding>(this.holdings);
    this.dataSource.paginator = this.paginator;
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<StockHolding>(this.holdings, $event);
    if (typeof(sortedData) !== 'undefined') this.dataSource = sortedData;
  }

}
