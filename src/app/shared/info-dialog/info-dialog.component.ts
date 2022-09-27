import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-info-dialog',
  templateUrl: './info-dialog.component.html',
  styleUrls: ['./info-dialog.component.scss'],
})
export class InfoDialogComponent implements OnInit {
  public data = { dialog_type: '', data: {} };

  constructor() {}

  ngOnInit(): void {}
}
