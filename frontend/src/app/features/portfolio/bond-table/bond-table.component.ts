import {
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
} from '@angular/core';
import { AssetHolding } from 'src/app/core/models/asset-holding';

@Component({
  selector: 'app-bond-table',
  templateUrl: './bond-table.component.html',
  styleUrls: ['./bond-table.component.scss'],
})
export class BondTableComponent implements OnInit, OnChanges {
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['holdings']) {
      this.updatePortfolio(changes['holdings'].currentValue);
    }
  }

  invested_amount: number = 0;
  current_amount: number = 0;

  @Input() holdings: AssetHolding[] = [];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'buyPrice', displayName: 'Buy Price', type: 'currency' },
    { name: 'currentPrice', displayName: 'LTP', type: 'currency' },
    { name: 'quantity', displayName: 'Qty', type: 'text' },
    { name: 'assetType', displayName: 'Type', type: 'snakecase' },
  ];

  ngOnInit(): void {
    this.updatePortfolio(this.holdings);
  }
  updatePortfolio(holdings: AssetHolding[]) {
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < holdings.length; i++) {
      this.invested_amount += holdings[i].buyPrice * holdings[i].quantity;
      this.current_amount += holdings[i].currentPrice * holdings[i].quantity;
    }
  }

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'BOND', data: data });
  }
}
