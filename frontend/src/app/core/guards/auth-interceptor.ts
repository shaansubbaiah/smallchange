import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { AuthService } from '../services/auth.service';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {
  constructor(public auth: AuthService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.auth.getToken())
      request = request.clone({
        setHeaders: {
          Authorization: `Bearer ${this.auth.getToken()}`
        }
      });
    return next.handle(request);
  }
}
