import { Component, OnInit } from '@angular/core';
import { AuthService } from './core/services/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit {
  title = 'SmallChange';
  showUserMenu : boolean = false;

  constructor(private authService: AuthService) {}

  ngOnInit() {
    this.authService.setInitialLoginStatus();
  }
}
