import { Injectable } from '@angular/core';
import {
  dummy_data_bonds,
  dummy_data_mfs,
  dummy_data_order,
  dummy_data_stocks,
  market_bonds,
  market_stocks,
} from '../../core/models/mock-data';
import { MarketAssets } from '../models/market-assets';
import { UserPortfolio } from '../models/user-portfolio';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private userId: string;

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

  getPortfolio(): UserPortfolio {
    return new UserPortfolio(
      this.getStockHolding(),
      this.getBondHolding(),
      this.getMFHolding()
    );
  }

  getMarketStocks() {
    return market_stocks;
  }

  getMarketBonds() {
    return market_bonds;
  }

  getMarketAssets(): MarketAssets {
    // we don't have dummy data for market MFs yet
    return new MarketAssets(this.getMarketStocks(), this.getMarketBonds(), []);
  }
}
