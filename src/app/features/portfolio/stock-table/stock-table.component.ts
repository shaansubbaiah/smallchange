import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { StockHolding } from 'src/app/core/models/stock-holding';
@Component({
  selector: 'app-stock-table',
  templateUrl: './stock-table.component.html',
  styleUrls: ['./stock-table.component.scss'],
})
export class StockTableComponent implements OnInit {
  invested_amount: number = 0;
  current_amount: number = 0;

  @Input() holdings!: StockHolding[];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'buy_price', displayName: 'Buy Price', type: 'currency' },
    { name: 'LTP', displayName: 'LTP', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
    { name: 'asset_class', displayName: 'Asset Class', type: 'text' },
  ];

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;

    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'stock', data: data });
  }
}
