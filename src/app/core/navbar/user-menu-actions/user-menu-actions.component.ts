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

  getUserInitials() : string {
    let firstName = this.getUserDetail('firstName'), lastName = this.getUserDetail('lastName');
    let initials = '';
    if (firstName.length > 0) initials += firstName.charAt(0);
    if (lastName.length > 0) initials += lastName.charAt(0);

    return initials;
  }

  getUserDetail(key : string) : any {
    let val = CommonUtils.getUserDetail(key);
    console.log(val);
    if (val === null) return '';
    else return val;
  }
}
