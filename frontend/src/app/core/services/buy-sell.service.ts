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

  public transact(data: AssetTransactionModel): Observable<TransactionResult> {
    console.log('data:');
    console.log(data);

    const requestEndpoint =
      data.transaction_type == 'buy'
        ? Constants.BUY_ENDPOINT
        : Constants.SELL_ENDPOINT;

    return this.httpClient.post<TransactionResult>(requestEndpoint, data);
  }
}
