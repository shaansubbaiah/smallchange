import { Component, OnInit } from '@angular/core';
import { Router, ResolveEnd } from '@angular/router';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'SmallChange';
  showUserMenu: boolean = false;
  isHomepage = false;

  constructor(private authService: AuthService, private router: Router) {
    this.router.events.subscribe((routerData) => {
      if (routerData instanceof ResolveEnd) {
        if (routerData.url === '/home') {
          this.isHomepage = true;
        } else {
          this.isHomepage = false;
        }
      }
    });
  }

  ngOnInit() {
    this.authService.setInitialLoginStatus();
  }
}
