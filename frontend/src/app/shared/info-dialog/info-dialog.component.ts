import { Component, OnInit } from '@angular/core';
import { TransactionResult } from 'src/app/core/models/transaction-result';
import { AlertService } from 'src/app/core/services/alert.service';
import { BuySellService } from 'src/app/core/services/buy-sell.service';
import { CommonUtils } from 'src/app/utils';

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
      `Trying to sell${
        this.data.index_type
      }\nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );

    this.buySellService.attemptSell({
      index_code: this.indexData.code,
      asset_class : this.indexData.asset_class,
      quantity: `${quantity}`,
      user_id: `${CommonUtils.getUserDetail('userName')}`,
    }).subscribe((result : TransactionResult) => {
      if (result.result === 'SUCCESS') {
        this.alertService.open({
          type: 'success',
          message: 'Purchased stock!',
        });
      }
      else {
        this.alertService.open({
          type: 'warning',
          message: 'Purchase failed!',
        });
      }
      console.log(JSON.stringify(result));
    });
  }

  onBuyIndex(quantity: number) {
    console.log(
      `Trying to buy  ${
        this.data.index_type
      } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    );

    this.buySellService.attemptBuy({
      index_code: this.indexData.code,
      asset_class : this.indexData.asset_class,
      quantity: `${quantity}`,
      user_id: `${CommonUtils.getUserDetail('userName')}`,
    }).subscribe((result : TransactionResult) => {
      if (result.result === 'SUCCESS') {
        this.alertService.open({
          type: 'success',
          message: 'Purchased stock!',
        });
      }
      else {
        this.alertService.open({
          type: 'warning',
          message: 'Purchase failed!',
        });
      }
      console.log(JSON.stringify(result));
    });
  }
}
