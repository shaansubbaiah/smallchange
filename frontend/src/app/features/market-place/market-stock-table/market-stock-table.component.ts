import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StockList } from 'src/app/core/models/stock-list';
@Component({
  selector: 'app-market-stock-table',
  templateUrl: './market-stock-table.component.html',
  styleUrls: ['./market-stock-table.component.scss'],
})
export class MarketStockTableComponent implements OnInit {
  @Input() holdings!: StockList[];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'buy_price', displayName: 'Buy Price', type: 'currency' },
    { name: 'LTP', displayName: 'LTP', type: 'currency' },
    { name: 'asset_class', displayName: 'Asset Class', type: 'text' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'stock', data: data });
  }
}
