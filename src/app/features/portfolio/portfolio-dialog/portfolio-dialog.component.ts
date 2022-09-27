import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-portfolio-dialog',
  templateUrl: './portfolio-dialog.component.html',
  styleUrls: ['./portfolio-dialog.component.scss'],
})
export class PortfolioDialogComponent implements OnInit {
  public data = { dialog_type: '', data: {} };

  constructor() {}

  ngOnInit(): void {}
}
