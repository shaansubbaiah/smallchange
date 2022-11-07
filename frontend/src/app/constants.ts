
export class Constants {
  public static readonly BACKEND_URL = "http://localhost:8080/api";
  public static readonly AUTH_ENDPOINT = this.BACKEND_URL + '/user/authenticate';
  public static readonly REGISTER_ENDPOINT = this.BACKEND_URL + '/user/register';
  public static readonly LOGOUT_ENDPOINT = this.BACKEND_URL + '/user/logout';
  public static readonly TRADE_HISTORY_ENDPOINT = this.BACKEND_URL + '/tradehistory';
  public static readonly STOCK_HOLDINGS_ENDPOINT = this.BACKEND_URL + '/holdings/stocks';
  public static readonly MF_HOLDINGS_ENDPOINT = this.BACKEND_URL + '/holdings/mfs';
  public static readonly BOND_HOLDINGS_ENDPOINT = this.BACKEND_URL + '/holdings/bonds';
  public static readonly PORTFOLIO_ENDPOINT = this.BACKEND_URL + '/portfolio';
  public static readonly STOCK_MP_ENDPOINT = this.BACKEND_URL + '/marketplace/stocks';
  public static readonly MF_MP_ENDPOINT = this.BACKEND_URL + '/marketplace/mfs';
  public static readonly BOND_MP_ENDPOINT = this.BACKEND_URL + '/marketplace/bonds';
  public static readonly BUY_ENDPOINT = this.BACKEND_URL + '/transactions/buy';
  public static readonly SELL_ENDPOINT = this.BACKEND_URL + '/transactions/sell';

}
