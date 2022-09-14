import { Injectable } from '@angular/core';
import {
  dummy_data_bonds,
  dummy_data_mfs,
  dummy_data_order,
  dummy_data_stocks,
} from '../../core/models/mock-data';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor() {}

  getTradeHistory() {
    return dummy_data_order;
  }

  getStockHolding() {
    return dummy_data_stocks;
  }

  getMFHolding() {
    return dummy_data_mfs;
  }

  getBondHolding() {
    return dummy_data_bonds;
  }
}
