import { Injectable } from '@angular/core';
import { BehaviorSubject, from, Observable, of } from 'rxjs';
import { User } from '../models/user';
import { CommonUtils } from 'src/app/utils';
import { HttpClient } from '@angular/common/http';
import { Constants } from 'src/app/constants';
import { map, filter, mergeMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  public isLoggedIn = new BehaviorSubject<any>(false);

  constructor(private httpClient: HttpClient) {}

  public authenticate(username: string, password: string): Observable<any> {
    return this.httpClient.post(Constants.AUTH_ENDPOINT, {
      username: username,
      password: password,
      rememberMe: true,
    });
  }

  registerUser(
    userId: string,
    name: string,
    email: string,
    password: string
  ): Observable<any> {
    console.log('in reg');
    return this.httpClient.post(Constants.REGISTER_ENDPOINT, {
      scUserId: userId,
      name: name,
      email: email,
      passwordHash: password,
      scUserRole: 'USER',
      scUserEnabled: true,
    });
  }

  createBankAccount(
    userId: string,
    accNo: string,
    bankName: string,
    accType: string
  ): Observable<any> {
    console.log('in bank');
    return this.httpClient.post(Constants.BANKACCT_ENDPOINT, {
      scUserId: userId,
      accNo: accNo,
      bankName: bankName,
      accType: accType,
      accBalance: parseFloat((5000 + Math.random() * 10000).toFixed(2)),
    });
  }

  getToken(): string | null {
    return CommonUtils.getUserDetail('jwt');
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
      if (currentUserJSON === null)
        throw new Error('Unable to fetch current user details!');
      let currentUser = JSON.parse(currentUserJSON);
      return new User(
        currentUser.userName,
        currentUser.firstName,
        currentUser.lastName,
        currentUser.email,
        currentUser.lastLogin,
        currentUser.jwt,
        currentUser.accounts
      );
    } catch (err) {
      console.log(err);
      return null;
    }
  }

  getAccountDetails(accNo: number): Observable<any> {
    return this.httpClient.get(Constants.BANKACCT_ENDPOINT, {
      params: { accNo: accNo },
    });
  }

  setSelectedAccount(accNo: number) {
    localStorage.setItem('selectedAccount', accNo.toString());
  }

  getSelectedAccount() {
    return localStorage.getItem('selectedAccount');
  }

  setInitialLoginStatus() {
    if (this.getToken()) {
      this.setLoggedIn(true);
    }
  }
}
