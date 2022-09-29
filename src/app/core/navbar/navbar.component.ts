import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
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

  @Output()
  userAvatarButtonClicked : EventEmitter<void> = new EventEmitter();

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit(): void {
    this.authService.getLoggedIn().subscribe((val : boolean) => {
      this.isLoggedIn = val;
      if (this.isLoggedIn) {
        let currentUser = this.authService.getUserDetails();

        if (currentUser === null || typeof currentUser == 'undefined')
          throw new Error('Unable to fetch user details!!');

        this.currentUser = currentUser;
        this.navLinks = [
          { name: 'Portfolio', url: '/portfolio' },
          { name: 'Trade History', url: '/trade-history' },
          { name: 'Marketplace', url: '/market-place' },
          { name: 'Preferences', url: '/preferences' },
        ];
      } else {
        this.navLinks = [{ name: 'Sign In', url: '/login' }];
      }
    });
  }

  logout() {
    console.log('logged out');
    this.authService.setLoggedIn(false);
    this.router.navigateByUrl('/login');
  }
}
