import { Injectable } from '@angular/core';
import { BehaviorSubject, from, Observable, of } from 'rxjs';

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
}
