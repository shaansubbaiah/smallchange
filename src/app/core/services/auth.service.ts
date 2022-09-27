import { Injectable } from '@angular/core';
import { BehaviorSubject, from, Observable, of } from 'rxjs';
import { users } from '../models/mock-data';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthService {

  public isLoggedIn = new BehaviorSubject<any>(false);

  constructor() {}

  public authenticate(username: string, password: string): boolean {
    return true; //username === 'admin' && password === 'admin';
  }

  public register(username: string, email: string, password: string): boolean {
    return true;
  }

  getToken(): boolean {
    return localStorage.getItem('token') ? true : false;
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
