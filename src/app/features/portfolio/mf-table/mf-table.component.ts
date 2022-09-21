import { Component, Input, OnInit } from '@angular/core';
import { MfHolding } from '../../../core/models/mf-holding';

@Component({
  selector: 'app-mf-table',
  templateUrl: './mf-table.component.html',
  styleUrls: ['./mf-table.component.scss'],
})
export class MfTableComponent implements OnInit {
  invested_amount: number = 0;
  current_amount: number = 0;

  @Input() holdings: MfHolding[] = [];

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'buy_price', displayName: 'Buy Price', type: 'currency' },
    { name: 'LTP', displayName: 'LTP', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
  ];

  constructor() {}

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }
}
