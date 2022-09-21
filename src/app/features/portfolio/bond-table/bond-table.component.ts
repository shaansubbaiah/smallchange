import { Component, Input, OnInit } from '@angular/core';
import { BondHolding } from '../../../core/models/bond-holding';

@Component({
  selector: 'app-bond-table',
  templateUrl: './bond-table.component.html',
  styleUrls: ['./bond-table.component.scss'],
})
export class BondTableComponent implements OnInit {
  invested_amount: number = 0;
  current_amount: number = 0;

  @Input() holdings: BondHolding[] = [];

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
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
}
