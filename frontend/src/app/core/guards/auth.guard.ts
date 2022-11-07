import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  RouterStateSnapshot,
  UrlTree,
  Router,
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { JwtHelperService } from "@auth0/angular-jwt";
import { AlertService } from '../services/alert.service';


@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private auth: AuthService, private alertService : AlertService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {

      if (this.isJWTValid()) {
        return true;
      } else {
        this.router.navigateByUrl('/login');
        this.alertService.open({ type: 'warning', message: 'Session expired! Please log in again!' });
        return false;
      }
  }
  isJWTValid() : boolean {
    let jwt = this.auth.getToken();
    if (jwt === null || typeof jwt === 'undefined' || jwt.length === 0) return false;

    let jwtService : JwtHelperService = new JwtHelperService();

    //let decodedJWT = jwtService.decodeToken(jwt);
    return !jwtService.isTokenExpired(jwt);

  }


}
