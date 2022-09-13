import { Injectable } from '@angular/core';
import { dummy_data_order , dummy_data_stocks} from '../../core/models/mock-data';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor() {}

  getTradeHistory() {
    return dummy_data_order;
  }
  getStockHolding(){
    return dummy_data_stocks;
  }
}
