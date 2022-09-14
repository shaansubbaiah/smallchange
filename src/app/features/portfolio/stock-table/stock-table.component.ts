import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { StockHolding } from 'src/app/core/models/stock-holding';

@Component({
  selector: 'app-stock-table',
  templateUrl: './stock-table.component.html',
  styleUrls: ['./stock-table.component.scss'],
})
export class StockTableComponent implements AfterViewInit {
  stocks: StockHolding[] = [];
  dataSource!: MatTableDataSource<StockHolding>;

  invested_amount: number = 0;
  current_amount: number = 0;

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
    this.stocks = this.dataService.getStockHolding();
    this.dataSource = new MatTableDataSource<StockHolding>(this.stocks);
    this.dataSource.paginator = this.paginator;

    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.stocks.length; i++) {
      this.invested_amount +=
        this.stocks[i].buy_price * this.stocks[i].quantity;
      this.current_amount += this.stocks[i].LTP * this.stocks[i].quantity;
    }
  }
}
