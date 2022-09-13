import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})

export class PortfolioComponent implements OnInit {
  constructor() { }
  @Input() fromMain!: boolean;
  eventTriggered: boolean = true;
  invested_amount: number = 0;
  current_amount: number = 0;
  funds: number = 0;
  ngOnInit(): void {
    
  }

  ngOnChanges() {
      this.eventTriggered?this.eventTriggered=false:this.eventTriggered=true;
      this.ngOnInit();
  }

}
