import { Component } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
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
export class InfoDialogComponent {
  public infoDialogData!: InfoDialogData;
  orderQuantity = 0;

  constructor(
    private alertService: AlertService,
    private buySellService: BuySellService,
    private dialogRef: MatDialogRef<InfoDialogComponent>
  ) {}

  onBuySell(quantity: number, transactionType: 'buy' | 'sell') {
    this.buySellService
      .transact({
        index_code: this.infoDialogData.data.code,
        index_type: this.infoDialogData.index_type,
        quantity: quantity,
        transaction_type: transactionType,
        user_id: CommonUtils.getUserDetail('userName') || '',
      })
      .subscribe({
        next: (res) => {
          console.log('result:');
          console.log(res);

          this.dialogRef.close();

          this.alertService.open({
            type: 'success',
            message: 'Transaction successfull.',
          });
        },
        error: (e) => {
          console.log('error:');
          console.log(e);

          this.dialogRef.close();

          this.alertService.open({
            type: 'error',
            message: 'Transaction failed.',
          });
        },
      });
  }
}
