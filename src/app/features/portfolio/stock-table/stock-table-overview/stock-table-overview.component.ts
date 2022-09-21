import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-stock-table-overview',
  templateUrl: './stock-table-overview.component.html',
  styleUrls: ['./stock-table-overview.component.scss'],
})
export class StockTableOverviewComponent implements OnInit {
  @Input() invested_amount: number = 0;
  @Input() current_amount: number = 0;

  constructor() {}

  ngOnInit(): void {}
}
