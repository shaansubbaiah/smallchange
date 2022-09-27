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
export class TradeHistoryComponent implements OnInit {
  tradeHistory: TradeStock[] = [];
  dataSource!: MatTableDataSource<TradeStock>;

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'price', displayName: 'Price', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
    { name: 'asset_class', displayName: 'Asset Class', type: 'text' },
    { name: 'trade_type', displayName: 'Action', type: 'buysell' },
    { name: 'date', displayName: 'Date', type: 'date' },
    { name: 'time', displayName: 'Time', type: 'time' },
  ];

  constructor(private dataService: DataService) {}

  ngOnInit() {
    this.tradeHistory = this.dataService.getTradeHistory();
  }
}
