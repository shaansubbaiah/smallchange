import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';
import { TradeStock } from '../../core/models/trade-stock';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';

@Component({
  selector: 'app-trade-history',
  templateUrl: './trade-history.component.html',
  styleUrls: ['./trade-history.component.scss'],
})
export class TradeHistoryComponent implements AfterViewInit {
  tradeHistory: TradeStock[] = [];
  dataSource!: MatTableDataSource<TradeStock>;

  displayedColumns: string[] = [
    'name',
    'code',
    'quantity',
    'price',
    'asset_class',
    'trade_type',
    'date',
    'time',
  ];

  constructor(private dataService: DataService) {}

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.tradeHistory = this.dataService.getTradeHistory();
    this.dataSource = new MatTableDataSource<TradeStock>(this.tradeHistory);
    this.dataSource.paginator = this.paginator;
  }
}
