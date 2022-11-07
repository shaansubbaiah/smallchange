import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import * as internal from 'stream';
import {
  dummy_data_bonds,
  dummy_data_mfs,
  dummy_data_order,
  dummy_data_stocks,
  market_bonds,
  market_mfs,
  market_stocks,
} from '../../core/models/mock-data';
import { MarketAssets } from '../models/market-assets';
import { UserPortfolio } from '../models/user-portfolio';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private userId: string;

  constructor(private httpClient : HttpClient) {
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

  public getMarketStocks(): Observable<any> {

    return this.httpClient.get(Constants.STOCK_MP_ENDPOINT, {     
    });
    
  }
  public getMarketMfs(): Observable<any>{
    return this.httpClient.get(Constants.MF_MP_ENDPOINT, {     
    });
  }

  public getMarketBonds(): Observable<any> {
    return this.httpClient.get(Constants.BOND_MP_ENDPOINT, {     
    });
  }

  getMarketAssets():Observable<any>[] {
    // we don't have dummy data for market MFs yet

    return [this.getMarketStocks(),this.getMarketBonds(),this.getMarketMfs()];
  }
}
