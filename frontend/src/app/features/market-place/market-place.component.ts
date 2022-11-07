import { Component, OnInit } from '@angular/core';
import { MarketAssets } from 'src/app/core/models/market-assets';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/info-dialog/info-dialog.component';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-market-place',
  templateUrl: './market-place.component.html',
  styleUrls: ['./market-place.component.scss'],
})
export class MarketPlaceComponent implements OnInit {
  public marketAssets: MarketAssets = new MarketAssets([], [], []);

  constructor(private dataService: DataService, public dialog: MatDialog) {}


  ngOnInit(): void {
    let asset : Observable<any>[] = this.dataService.getMarketAssets();
    asset[0].subscribe(result => {
      this.marketAssets = new MarketAssets(result, this.marketAssets.marketBonds, this.marketAssets.marketMfs);
      console.log(result);
    });
    asset[1].subscribe(result => {
      this.marketAssets = new MarketAssets(this.marketAssets.marketStocks, result, this.marketAssets.marketMfs);
      console.log(result);
    });
    asset[2].subscribe(result => {
      this.marketAssets = new MarketAssets(this.marketAssets.marketStocks, this.marketAssets.marketBonds, result);
      console.log(result);
    });
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      minWidth: '400px',
    });
    dialogRef.componentInstance.data = data;
  }
}
