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

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'price', displayName: 'Price', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
    { name: 'asset_class', displayName: 'Asset Class', type: 'snakecase' },
    { name: 'trade_type', displayName: 'Action', type: 'uppercase' },
    { name: 'date', displayName: 'Date', type: 'date' },
    { name: 'time', displayName: 'Time', type: 'time' },
  ];

  constructor(private dataService: DataService) {}

  ngOnInit() {
     this.dataService.getTradeHistory().subscribe(result =>{
        this.tradeHistory= result;
     });
    }

    
  }

