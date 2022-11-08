import { AssetHolding } from "./asset-holding";


export class UserPortfolio {
  private _stockHoldings!: AssetHolding[];
  private _bondHoldings!: AssetHolding[];
  private _mfHoldings!: AssetHolding[];

  constructor(stocks: AssetHolding[], bonds: AssetHolding[], mfs: AssetHolding[]) {
    this.mfHoldings = mfs;
    this.bondHoldings = bonds;
    this.stockHoldings = stocks;
  }

  public get stockHoldings(): AssetHolding[] {
    return this._stockHoldings;
  }

  public set stockHoldings(value: AssetHolding[]) {
    this._stockHoldings = value;
  }

  public get bondHoldings(): AssetHolding[] {
    return this._bondHoldings;
  }

  public set bondHoldings(value: AssetHolding[]) {
    this._bondHoldings = value;
  }

  public get mfHoldings(): AssetHolding[] {
    return this._mfHoldings;
  }

  public set mfHoldings(value: AssetHolding[]) {
    this._mfHoldings = value;
  }
}
