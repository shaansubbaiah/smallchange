import { Component, OnInit } from '@angular/core';
import { MarketAssets } from 'src/app/core/models/market-assets';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/info-dialog/info-dialog.component';
@Component({
  selector: 'app-market-place',
  templateUrl: './market-place.component.html',
  styleUrls: ['./market-place.component.scss'],
})
export class MarketPlaceComponent implements OnInit {
  public marketAssets!: MarketAssets;

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.marketAssets = this.dataService.getMarketAssets();
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      minWidth: '400px',
    });
    dialogRef.componentInstance.data = data;
  }
}
