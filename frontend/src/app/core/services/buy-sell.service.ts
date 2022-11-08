import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Constants } from 'src/app/constants';
import { AssetTransactionModel } from '../models/asset-transaction-model';
import { TransactionResult } from '../models/transaction-result';

@Injectable({
  providedIn: 'root',
})
export class BuySellService {
  constructor(private httpClient: HttpClient) {}

  transact(data: AssetTransactionModel): Observable<TransactionResult> {
    console.log('data:');
    console.log(data);

    const requestEndpoint =
      data.transaction_type == 'buy'
        ? Constants.BUY_ENDPOINT
        : Constants.SELL_ENDPOINT;

    this.httpClient.post(requestEndpoint, data).subscribe((result) => {
      console.log('result');
      console.log(result);
    });

    return of({
      result: 'SUCCESS',
      errorCode: `${null}`,
      description: 'Transaction successful!',
      payload: data,
    });
  }

  // attemptBuy(buyData: AssetTransactionModel): Observable<TransactionResult> {
  //   let array: TransactionResult[] = [
  //     {
  //       result: 'SUCCESS',
  //       errorCode: `${null}`,
  //       description: 'Transaction successful!',
  //       payload: buyData,
  //     },
  //     {
  //       result: 'FAILURE',
  //       errorCode: '500',
  //       description: 'Internal server error',
  //       payload: buyData,
  //     },
  //   ];

  //   return of(array[Math.floor(Math.random() * array.length)]);
  // }

  // attemptSell(sellData: AssetTransactionModel): Observable<TransactionResult> {
  //   let array: TransactionResult[] = [
  //     {
  //       result: 'SUCCESS',
  //       errorCode: `${null}`,
  //       description: 'Transaction successful!',
  //       payload: sellData,
  //     },
  //     {
  //       result: 'FAILURE',
  //       errorCode: '500',
  //       description: 'Internal server error',
  //       payload: sellData,
  //     },
  //   ];

  //   return of(array[Math.floor(Math.random() * array.length)]);
  // }
}
