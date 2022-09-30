import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { CommonUtils } from 'src/app/utils';
import { User } from '../models/user';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  isLoggedIn: boolean = false;
  currentUser!: User;
  navLinks: any;

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe((val: boolean) => {
      this.isLoggedIn = val;
      if (this.isLoggedIn) {
        let currentUser = this.authService.getUserDetails();

        if (currentUser === null || typeof currentUser == 'undefined')
          throw new Error('Unable to fetch user details!!');

        this.currentUser = currentUser;
        this.navLinks = [
          { name: 'Portfolio', url: '/portfolio' },
          // { name: 'Trade History', url: '/trade-history' },
          { name: 'Marketplace', url: '/market-place' },
        ];
      } else {
        this.navLinks = [{ name: 'Sign In', url: '/login' }];
      }
    });
  }

  getUserInitials(): string {
    let firstName = this.getUserDetail('firstName'),
      lastName = this.getUserDetail('lastName');
    let initials = '';
    if (firstName.length > 0) initials += firstName.charAt(0);
    if (lastName.length > 0) initials += lastName.charAt(0);

    return initials;
  }

  getUserDetail(key: string): any {
    let val = CommonUtils.getUserDetail(key);
    if (val === null) return '';
    else return val;
  }

  logout() {
    console.log('logged out');
    this.authService.setLoggedIn(false);
    this.router.navigateByUrl('/login');
  }
}
