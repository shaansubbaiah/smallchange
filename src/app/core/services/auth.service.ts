import { Injectable } from '@angular/core';
import { BehaviorSubject, from, Observable, of } from 'rxjs';
import { users } from '../models/mock-data';
import { User } from '../models/user';
import * as CryptoJS from "crypto-js";
import { CommonUtils } from 'src/app/utils';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  public isLoggedIn = new BehaviorSubject<any>(false);

  constructor() {}

  public authenticate(username: string, password: string): Observable<boolean> {
    let passwordHash = CryptoJS.SHA256(password).toString();
    for (let user of users) {
      if (username === user.userName)
        if (passwordHash === user.passwordHash) {
          console.log('login success');
          localStorage.setItem('currentUser', JSON.stringify(user));
          return of(true);
        }
    }
    return of(false);
  }

  public register(username: string, email: string, password: string): boolean {
    return true;
  }

  getToken(): boolean {
    return CommonUtils.getUserDetail('token') ? true : false;
  }

  saveToLocalStorage(data: any) {
    for (const e in data) {
      localStorage.setItem(e, data[e]);
    }
  }

  clearLocalStorage() {
    localStorage.clear();
  }

  getLoggedIn() {
    return this.isLoggedIn.asObservable();
  }

  setLoggedIn(val: boolean) {
    this.isLoggedIn.next(val);

    if (val == true) {
      this.saveToLocalStorage({
        token: 'DUMMY TOKEN',
        user: JSON.stringify({ username: 'DUMMY USER' }),
      });
    } else {
      this.clearLocalStorage();
    }
  }

  getUserDetails(): User {
    return users[Math.floor(Math.random()*users.length)];
  }

  setInitialLoginStatus() {
    if (this.getToken()) {
      this.setLoggedIn(true);
    }
  }
}
