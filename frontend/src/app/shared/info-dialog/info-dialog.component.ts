import { Component, OnInit } from '@angular/core';
import { AlertService } from 'src/app/core/services/alert.service';
import { BuySellService } from 'src/app/core/services/buy-sell.service';

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss'],
})
export class InfoDialogComponent implements OnInit {
  public data = { index_type: '', data: {} };
  indexData: any;
  orderQuantity = 0;

  constructor(private alertService: AlertService, private buySellService : BuySellService) {}

  ngOnInit(): void {
    this.indexData = this.data.data;
  }

  onSellIndex(quantity: number) {
    console.log(
      `Sold ${
        this.data.index_type
      } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );

    // send an event back to the portfolio
    // from there,
    // 1. use a service to buy/sell
    // 2. display alert on success/fail
    // 3. close the dialog

    this.alertService.open({ type: 'success', message: 'Sold stock!' });
  }

  onBuyIndex(quantity: number) {
    console.log(
      `Purchased  ${
        this.data.index_type
      } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );

    this.alertService.open({
      type: 'success',
      message: 'Purchased stock!',
    });
  }
}
