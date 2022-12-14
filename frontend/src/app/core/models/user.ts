import { BankAccount } from './bank-account';

export class User {
  userName: string;
  firstName: string;
  lastName: string;
  email: string;
  lastLogin: number; //last successful login timestamp; to be updated in login screen
  jwt: string;
  accounts: number[];

  constructor(
    userName: string,
    firstName: string,
    lastName: string,
    email: string,
    lastLogin: number,
    token: string,
    accounts: number[]
  ) {
    this.userName = userName.trim();
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    this.email = email.trim();
    this.lastLogin = lastLogin;
    this.jwt = token.trim();
    this.accounts = accounts;
  }
}
