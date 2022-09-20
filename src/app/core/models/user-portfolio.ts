import { BondHolding } from "./bond-holding";
import { MfHolding } from "./mf-holding";
import { StockHolding } from "./stock-holding";

export class UserPortfolio {

  private _stockHoldings!: StockHolding[];

  public get stockHoldings(): StockHolding[] {
    return this._stockHoldings;
  }
  public set stockHoldings(value: StockHolding[]) {
    this._stockHoldings = value;
  }
  private _bondHoldings!: BondHolding[];
  public get bondHoldings(): BondHolding[] {
    return this._bondHoldings;
  }
  public set bondHoldings(value: BondHolding[]) {
    this._bondHoldings = value;
  }
  private _mfHoldings!: MfHolding[];
  public get mfHoldings(): MfHolding[] {
    return this._mfHoldings;
  }
  public set mfHoldings(value: MfHolding[]) {
    this._mfHoldings = value;
  }

  constructor(stocks: StockHolding[], bonds: BondHolding[], mfs: MfHolding[]) {
    this.mfHoldings = mfs;
    this.bondHoldings = bonds;
    this.stockHoldings = stocks;
  }
}
