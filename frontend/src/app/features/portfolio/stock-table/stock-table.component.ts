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
  selector: 'app-stock-table',
  templateUrl: './stock-table.component.html',
  styleUrls: ['./stock-table.component.scss'],
})
export class StockTableComponent implements OnInit, OnChanges {
  ngOnChanges(changes: SimpleChanges): void {
    if (changes['holdings']) {
      this.updatePortfolio(changes['holdings'].currentValue);
    }
  }
  invested_amount: number = 0;
  current_amount: number = 0;

  @Input() holdings!: AssetHolding[];
  @Output() openDialogEvent = new EventEmitter<any>();

  tableColumns = [
    { name: 'name', displayName: 'Name', type: 'text' },
    { name: 'code', displayName: 'Code', type: 'text' },
    { name: 'buyPrice', displayName: 'Buy Price', type: 'currency' },
    { name: 'currentPrice', displayName: 'LTP', type: 'currency' },
    { name: 'quantity', displayName: 'Quantity', type: 'text' },
    { name: 'assetType', displayName: 'Type', type: 'snakecase' },
  ];

  ngOnInit(): void {}

  openDialog(data: any) {
    this.openDialogEvent.emit({ index_type: 'STOCK', data: data });
  }

  updatePortfolio(holdings: AssetHolding[]) {
    this.invested_amount = 0;
    this.current_amount = 0;
    if (holdings != null) {
      for (var i = 0; i < holdings.length; i++) {
        this.invested_amount += holdings[i].buyPrice * holdings[i].quantity;
        this.current_amount += holdings[i].currentPrice * holdings[i].quantity;
      }
    }
    console.log(
      'updated! new values: ' + this.invested_amount + ',' + this.current_amount
    );
  }
}
