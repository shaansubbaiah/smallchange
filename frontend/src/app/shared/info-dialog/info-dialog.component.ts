import { Component, OnInit } from '@angular/core';
import { TransactionResult } from 'src/app/core/models/transaction-result';
import { AlertService } from 'src/app/core/services/alert.service';
import { BuySellService } from 'src/app/core/services/buy-sell.service';
import { CommonUtils } from 'src/app/utils';

interface InfoDialogData {
  index_type: 'stock' | 'mf' | 'bond';
  data: any;
}

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss'],
})
export class InfoDialogComponent implements OnInit {
  public infoDialogData!: InfoDialogData;
  // indexData: any;
  orderQuantity = 0;

  constructor(
    private alertService: AlertService,
    private buySellService: BuySellService
  ) {}

  ngOnInit(): void {
    // this.indexData = this.data.data;
  }

  onSellIndex(quantity: number) {
    // console.log(
    //   `Trying to sell${
    //     this.data.index_type
    //   }\nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    // );

    this.buySellService
      .transact({
        index_code: this.infoDialogData.data.code,
        index_type: this.infoDialogData.index_type,
        quantity: quantity,
        transaction_type: 'sell',
        user_id: CommonUtils.getUserDetail('userName') || '',
      })
      .subscribe({
        next: (res) => {
          console.log('result sell:');
          console.log(res);
        },
        error: (e) => {
          console.log(e);
        },
      });

    // this.buySellService
    //   .attemptSell({
    //     index_code: this.indexData.code,
    //     quantity: quantity,
    //     user_id: CommonUtils.getUserDetail('userName') || '',
    //   })
    //   .subscribe((result: TransactionResult) => {
    //     if (result.result === 'SUCCESS') {
    //       this.alertService.open({
    //         type: 'success',
    //         message: 'Sold stock!',
    //       });
    //     } else {
    //       this.alertService.open({
    //         type: 'error',
    //         message: 'Sell failed!',
    //       });
    //     }
    //     console.log(JSON.stringify(result));
    //   });
  }

  onBuyIndex(quantity: number) {
    // console.log(
    //   `Trying to buy  ${
    //     this.data.index_type
    //   } \nQuantity: ${quantity} \nIndex info: ${JSON.stringify(this.indexData)}`
    // );

    this.buySellService
      .transact({
        index_code: this.infoDialogData.data.code,
        index_type: this.infoDialogData.index_type,
        quantity: quantity,
        transaction_type: 'buy',
        user_id: CommonUtils.getUserDetail('userName') || '',
      })
      .subscribe({
        next: (res) => {
          console.log('result buy:');
          console.log(res);
        },
        error: (e) => {
          console.log(e);
        },
      });

    // this.buySellService
    //   .attemptBuy({
    //     index_code: this.indexData.code,
    //     quantity: quantity,
    //     user_id: CommonUtils.getUserDetail('userName') || '',
    //   })
    //   .subscribe((result: TransactionResult) => {
    //     if (result.result === 'SUCCESS') {
    //       this.alertService.open({
    //         type: 'success',
    //         message: 'Purchased stock!',
    //       });
    //     } else {
    //       this.alertService.open({
    //         type: 'error',
    //         message: 'Purchase failed!',
    //       });
    //     }
    //     console.log(JSON.stringify(result));
    //   });
  }
}
