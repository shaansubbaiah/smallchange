import { BondList } from './bond-list';
import { MfList } from './mf-list';
import { StockList } from './stock-list';

export class MarketAssets {
  private _marketStocks!: StockList[];
  private _marketBonds!: BondList[];
  private _marketMfs!: MfList[];

  constructor(stocks: StockList[], bonds: BondList[], mfs: MfList[]) {
    this.marketMfs = mfs;
    this.marketBonds = bonds;
    this.marketStocks = stocks;
  }

  public get marketStocks(): StockList[] {
    return this._marketStocks;
  }

  public set marketStocks(value: StockList[]) {
    this._marketStocks = value;
  }

  public get marketBonds(): BondList[] {
    return this._marketBonds;
  }

  public set marketBonds(value: BondList[]) {
    this._marketBonds = value;
  }

  public get marketMfs(): MfList[] {
    return this._marketMfs;
  }

  public set marketMfs(value: MfList[]) {
    this._marketMfs = value;
  }
}
