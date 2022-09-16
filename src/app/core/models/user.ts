import { BankAccount } from "./bank-account";

export interface User {
  userName: string;
  email: string;
  passwordHash: string;
  lastLogin: number; //last successful login timestamp; to be updated in login screen
  bankAccounts: BankAccount[];
}
