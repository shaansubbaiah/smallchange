import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { TransactionResult } from '../models/transaction-result';

@Injectable({
  providedIn: 'root'
})
export class BuySellService {

  constructor() { }

  attemptBuy(buyData : any) : Observable<TransactionResult> {
    return of({
    });
  }
}
