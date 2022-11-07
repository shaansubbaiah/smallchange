import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { BondList } from 'src/app/core/models/bond-list';
@Component({
  selector: 'app-market-bond-table',
  templateUrl: './market-bond-table.component.html',
  styleUrls: ['./market-bond-table.component.scss'],
})
export class MarketBondTableComponent implements OnInit {
  @Input() holdings: BondList[] = [];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'interestRate', displayName: 'Interest Rate', type: 'percentile' },
    { name: 'currentPrice', displayName: 'LTP', type: 'currency' },
    { name: 'quantity', displayName: 'Qty', type: 'text' },
    { name: 'bondType', displayName: 'Asset Class', type: 'snakecase' },
    { name: 'exchangeName', displayName: 'Exchange', type: 'text' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'bond', data: data });
  }
}
