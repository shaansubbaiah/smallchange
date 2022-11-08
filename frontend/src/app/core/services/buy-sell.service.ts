import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { AssetTransactionModel } from '../models/asset-transaction-model';
import { TransactionResult } from '../models/transaction-result';

@Injectable({
  providedIn: 'root',
})
export class BuySellService {
  constructor() {}

  transact(data: AssetTransactionModel): Observable<TransactionResult> {
    console.log(data);

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
