import { BankAccount } from './bank-account';

export class User {
  userName: string;
  firstName: string;
  lastName: string;
  email: string;
  passwordHash: string;
  lastLogin: number; //last successful login timestamp; to be updated in login screen
  bankAccounts: BankAccount[];
  token: string;

  constructor(
    userName: string,
    firstName: string,
    lastName: string,
    email: string,
    passwordHash: string,
    lastLogin: number,
    bankAccounts: BankAccount[],
    token: string
  ) {
    this.userName = userName.trim();
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    this.email = email.trim();
    this.passwordHash = passwordHash.trim();
    this.lastLogin = lastLogin;
    this.bankAccounts = bankAccounts;
    this.token = token.trim();
  }
}
