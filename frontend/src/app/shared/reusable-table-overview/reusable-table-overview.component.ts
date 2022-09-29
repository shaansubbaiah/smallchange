import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-reusable-table-overview',
  templateUrl: './reusable-table-overview.component.html',
  styleUrls: ['./reusable-table-overview.component.scss'],
})
export class ReusableTableOverviewComponent implements OnInit {
  @Input() invested_amount: number = 0;
  @Input() current_amount: number = 0;

  constructor() {}

  ngOnInit(): void {}
}
