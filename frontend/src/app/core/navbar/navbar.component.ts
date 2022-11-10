import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonUtils } from 'src/app/utils';
import { User } from '../models/user';
import { AuthService } from '../services/auth.service';

// interface BankAccount {

// }

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  currentUser!: User;
  navLinks: any;
  userInitials: string = '';
  userAccounts: any[] = [];

  constructor(private authService: AuthService, public router: Router) {}

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe((val: boolean) => {
      this.isLoggedIn = val;
      if (this.isLoggedIn) {
        let currentUser = this.authService.getUserDetails();

        if (currentUser === null || typeof currentUser == 'undefined')
          throw new Error('Unable to fetch user details!!');

        this.currentUser = currentUser;
        this.navLinks = [
          { name: 'Home', url: '/home' },
          { name: 'Portfolio', url: '/portfolio' },
          { name: 'Marketplace', url: '/market-place' },
        ];
      } else {
        this.navLinks = [{ name: 'Log In', url: '/login' }];
      }

      this.getUserInitials();

      // this.updateUserAccounts();
    });
  }

  getUserInitials() {
    let initials = '';
    if (this.currentUser == null) {
      this.userInitials = '';
      return;
    }
    if (this.currentUser.firstName.length > 0)
      initials += this.currentUser.firstName.charAt(0);
    if (this.currentUser.lastName.length > 0)
      initials += this.currentUser.lastName.charAt(0);

    this.userInitials = initials;
  }

  getUserBgColor(): string {
    if (this.currentUser != null)
      return CommonUtils.getPseudoRandomColor(
        this.currentUser.firstName + this.currentUser.lastName
      );
    else return '';
  }

  updateUserAccounts() {
    this.currentUser.accounts.forEach((accNo) => {
      this.userAccounts = [];
      this.getAccountDetails(accNo);
    });
    console.log(this.userAccounts);
  }

  getAccountDetails(accNo: number) {
    this.authService.getAccountDetails(accNo).subscribe((res) => {
      this.userAccounts.push(res);
    });
  }

  onAccountClick(accNo: number) {
    console.log(accNo);
  }

  logout() {
    this.authService.setLoggedIn(false);
    this.router.navigateByUrl('/login');
  }
}
