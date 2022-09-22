import { Component, OnInit } from '@angular/core';
import { MarketAssets } from 'src/app/core/models/market-assets';
import { DataService } from 'src/app/core/services/data.service';

@Component({
  selector: 'app-market-place',
  templateUrl: './market-place.component.html',
  styleUrls: ['./market-place.component.scss'],
})
export class MarketPlaceComponent implements OnInit {
  public marketAssets!: MarketAssets;

  constructor(private dataService: DataService) {}

  ngOnInit(): void {
    this.marketAssets = this.dataService.getMarketAssets();
  }
}
