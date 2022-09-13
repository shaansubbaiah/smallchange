import { Component, Input, OnInit } from '@angular/core';
import { StockHolding } from '../../core/models/stock-holding';
import { dummy_data_stocks } from '../../core/models/mock-data';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss']
})


export class PortfolioComponent implements OnInit {

 
  data: StockHolding[] = dummy_data_stocks;
  invested_amount: number = 0;
  current_amount: number = 0;
 

  constructor() {
  }
  ngOnChanges() {
    this.ngOnInit();
    
  }
  
  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.data.length; i++) {
      this.invested_amount += (this.data[i].buy_price * this.data[i].quantity)
      this.current_amount += (this.data[i].LTP * this.data[i].quantity)
    }
  }
}
