import { BankAccount } from "./bank-account";

export class User {
  userName: string;
  firstName : string;
  lastName : string;
  email: string;
  passwordHash: string;
  lastLogin: number; //last successful login timestamp; to be updated in login screen
  bankAccounts: BankAccount[];


  constructor(
    userName: string,
    firstName: string,
    lastName: string,
    email: string,
    passwordHash: string,
    lastLogin: number,
    bankAccounts: BankAccount[]
) {
    this.userName = userName.trim();
    this.firstName = firstName.trim();
    this.lastName = lastName.trim();
    this.email = email.trim();
    this.passwordHash = passwordHash.trim();
    this.lastLogin = lastLogin;
    this.bankAccounts = bankAccounts;
  }


  getInitials() : string {
    let initials : string = '';
    if (this.firstName && this.firstName.length > 0) {
      initials += this.firstName.charAt(0);
    }
    if (this.lastName && this.lastName.length > 0) {
      initials += this.lastName.charAt(0);
    }
    return initials;
  }
}
