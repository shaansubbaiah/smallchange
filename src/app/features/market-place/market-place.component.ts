import { Component, OnInit } from '@angular/core';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';

@Component({
  selector: 'app-market-place',
  templateUrl: './market-place.component.html',
  styleUrls: ['./market-place.component.scss']
})
export class MarketPlaceComponent implements OnInit {

  public userPortfolio!: UserPortfolio;

  private dataService: DataService = new DataService;

  public getUserPortfolio(): UserPortfolio {
    return this.dataService.getPortfolio();
  }

  ngOnInit(): void {
    this.userPortfolio = this.getUserPortfolio();
  }

}
