import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StockList } from 'src/app/core/models/stock-list';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-market-stock-table',
  templateUrl: './market-stock-table.component.html',
  styleUrls: ['./market-stock-table.component.scss'],
})
export class MarketStockTableComponent implements OnInit {
  @Input() holdings!: StockList[];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'currentPrice', displayName: 'Price', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
    { name: 'stockType', displayName: 'Asset Class', type: 'snakecase' },
    { name: 'exchangeName', displayName: 'Exchange', type: 'uppercase' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'STOCK', data: data });
  }
}
