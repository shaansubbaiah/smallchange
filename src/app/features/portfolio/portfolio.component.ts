import { Component, Input, OnInit } from '@angular/core';
import { BondHolding } from 'src/app/core/models/bond-holding';
import { MfHolding } from 'src/app/core/models/mf-holding';
import { StockHolding } from 'src/app/core/models/stock-holding';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss'],
})
export class PortfolioComponent implements OnInit {

  public userPortfolio!: UserPortfolio;

  private dataService: DataService = new DataService;

  public getUserPortfolio(): UserPortfolio {
    return this.dataService.getPortfolio();
  }

  ngOnInit(): void {
    this.userPortfolio = this.getUserPortfolio();
  }
}
