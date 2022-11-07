import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MfList } from 'src/app/core/models/mf-list';

@Component({
  selector: 'app-market-mf-table',
  templateUrl: './market-mf-table.component.html',
  styleUrls: ['./market-mf-table.component.scss'],
})
export class MarketMfTableComponent implements OnInit {
  @Input() holdings!: MfList[];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'currentPrice', displayName: 'LTP', type: 'currency' },
    { name: 'interestRate', displayName: 'Interest Rate', type: 'percentile' },
    { name: 'mfType', displayName: 'Type', type: 'snakecase' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'mf', data: data });
  }
}
