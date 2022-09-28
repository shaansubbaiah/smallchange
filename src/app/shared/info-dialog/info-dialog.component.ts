import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss'],
})
export class InfoDialogComponent implements OnInit {
  public data = { index_type: '', data: {} };
  indexData: any;
  orderQuantity = 0;

  constructor() {}

  ngOnInit(): void {
    this.indexData = this.data.data;
  }

  onSellIndex(quantity: number) {
    console.log(
      `Sold ${
        this.data.index_type
      } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );
  }

  onBuyIndex(quantity: number) {
    console.log(
      `Purchased  ${
        this.data.index_type
      } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );
  }
}
