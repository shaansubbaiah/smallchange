
export class Constants {
  public static readonly BACKEND_URL = "http://localhost:8080/api";
  public static readonly AUTH_ENDPOINT = this.BACKEND_URL + '/user/authenticate';
  public static readonly REGISTER_ENDPOINT = this.BACKEND_URL + '/user/register';
  public static readonly LOGOUT_ENDPOINT = this.BACKEND_URL + '/user/logout';
}
