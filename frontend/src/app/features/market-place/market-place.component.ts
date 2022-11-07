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
  public marketAssets!: MarketAssets;

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  
  ngOnInit(): void {
    let asset : Observable<any>[] =  this.dataService.getMarketAssets();
    asset[0].subscribe(result=>this.marketAssets.marketStocks=result);
    asset[1].subscribe(result=>this.marketAssets.marketBonds=result);
    asset[2].subscribe(result=>this.marketAssets.marketMfs=result);
    
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      minWidth: '400px',
    });
    dialogRef.componentInstance.data = data;
  }
}
