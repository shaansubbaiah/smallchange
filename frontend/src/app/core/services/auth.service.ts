import { Injectable } from '@angular/core';
import { BehaviorSubject, from, Observable, of } from 'rxjs';
import { users } from '../models/mock-data';
import { User } from '../models/user';
import { CommonUtils } from 'src/app/utils';
import { HttpClient } from '@angular/common/http';
import { Constants } from 'src/app/constants';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  public isLoggedIn = new BehaviorSubject<any>(false);

  constructor(private httpClient : HttpClient) {}

  public authenticate(username: string, password: string): Observable<boolean> {
    this.httpClient.post(Constants.AUTH_ENDPOINT, {
      username: username,
      password: password,
      rememberMe: true
    }).subscribe((result : any) => {
          const jwt = result.jwt;
          console.log('login success');
          let user = new User(username, result.firstName, result.lastName, result.email, result.lastLoginTimestamp, jwt);
          localStorage.setItem('currentUser', JSON.stringify(user));
          return of(true);
    });
  }

  public register(username: string, email: string, password: string): boolean {
    return true;
  }

  getToken(): string | null {
    return CommonUtils.getUserDetail('token');
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

    if (!val) {
      this.clearLocalStorage();
    }
  }

  getUserDetails(): User | null {
    let currentUserJSON = localStorage.getItem('currentUser');
    try {
      if (currentUserJSON === null) throw new Error('Unable to fetch current user details!');
      let currentUser = JSON.parse(currentUserJSON);
      return new User(
        currentUser.userName,
        currentUser.firstName,
        currentUser.lastName,
        currentUser.email,
        currentUser.passwordHash,
        currentUser.lastLogin,
        currentUser.bankAccounts,
        currentUser.token
      );
    } catch (err) {
      console.log(err);
      return null;
    }
  }

  setInitialLoginStatus() {
    if (this.getToken()) {
      this.setLoggedIn(true);
    }
  }
}
