import { Component, OnInit } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';
import { TradeStock } from '../../core/models/trade-stock';

@Component({
  selector: 'app-trade-history',
  templateUrl: './trade-history.component.html',
  styleUrls: ['./trade-history.component.scss'],
})
export class TradeHistoryComponent implements OnInit {
  tradeHistory: TradeStock[] = [];

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

  ngOnInit(): void {
    this.tradeHistory = this.dataService.getTradeHistory();
  }
}
