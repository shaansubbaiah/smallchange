import { Injectable } from '@angular/core';
import {
  dummy_data_bonds,
  dummy_data_mfs,
  dummy_data_order,
  dummy_data_stocks,
} from '../../core/models/mock-data';
import { UserPortfolio } from '../models/user-portfolio';

@Injectable({
  providedIn: 'root',
})
export class DataService {

  getPortfolio(): UserPortfolio {
    return new UserPortfolio(this.getStockHolding(), this.getBondHolding(), this.getMFHolding());
  }

  private userId : string;

  constructor() {
    console.info('Data access service created!');
    this.userId = '';
  }

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
