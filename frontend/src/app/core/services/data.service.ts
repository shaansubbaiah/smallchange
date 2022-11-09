import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Constants } from 'src/app/constants';
import { CommonUtils } from 'src/app/utils';

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
    return [];
    //return dummy_data_order;
  }

  getStockHolding(userId : string) { }

  getMFHolding(userId : string) { }

  getBondHolding(userId : string) { }

  getPortfolio(): Observable<any> {
    let userId = CommonUtils.getUserDetail('userName');
    if (userId == null)
      throw new Error('User ID to fetch portfolio is null!');

      return this.httpClient.get(Constants.PORTFOLIO_ENDPOINT, {
      });
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
