import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor() {}

  public authenticate(username: string, password: string): boolean {
    return true; //username === 'admin' && password === 'admin';
  }
}
