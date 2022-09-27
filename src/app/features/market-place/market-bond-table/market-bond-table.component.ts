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
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'interest', displayName: 'Interest', type: 'percentile' },
    { name: 'asset_class', displayName: 'Asset Class', type: 'text' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ dialog_type: 'bond', data: data });
  }
}
