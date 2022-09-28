import { Component, Input, OnInit } from '@angular/core';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-user-menu-actions',
  templateUrl: './user-menu-actions.component.html',
  styleUrls: ['./user-menu-actions.component.scss']
})
export class UserMenuActionsComponent implements OnInit {

  constructor() { }

  ngOnInit(): void { }

  getUserName() : string {
    let username = CommonUtils.getUserDetail('username');
    if (username === null) return '';
    else return username;
  }

}
