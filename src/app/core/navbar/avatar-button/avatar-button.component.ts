import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-avatar-button',
  templateUrl: './avatar-button.component.html',
  styleUrls: ['./avatar-button.component.scss'],
})
export class AvatarButtonComponent implements OnInit {
  @Input() buttonText: string = '';

  showUserMenu : boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  ngOnInit(): void {}
}
