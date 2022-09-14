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
  }
}
