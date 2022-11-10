import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';
import { TradeStock } from '../../core/models/trade-stock';
import { MatTableDataSource } from '@angular/material/table';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-trade-history',
  templateUrl: './trade-history.component.html',
  styleUrls: ['./trade-history.component.scss'],
})
export class TradeHistoryComponent implements OnInit {
  tradeHistory: TradeStock[] = [];
  dataSource!: MatTableDataSource<TradeStock>;
  isLoading: boolean = false;

  tableColumns = [
    { name: 'assetCode', displayName: 'Code', type: 'text' },
    { name: 'tradePrice', displayName: 'Price', type: 'currency' },
    { name: 'tradeQuantity', displayName: 'Quantity', type: 'text' },
    { name: 'assetType', displayName: 'Asset Class', type: 'uppercase' },
    { name: 'tradeType', displayName: 'Action', type: 'uppercase' },
    { name: 'tradeDate', displayName: 'Date', type: 'date' },
  ];

  constructor(private dataService: DataService) {}

  fetchData() {
    this.dataService.getTradeHistory().subscribe((result) => {
      this.tradeHistory = result;
    });
  }

  ngOnInit() {
    this.fetchData();
  }
}
