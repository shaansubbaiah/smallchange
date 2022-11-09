import { AfterViewInit, Component, OnInit } from '@angular/core';
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
export class MarketPlaceComponent implements OnInit, AfterViewInit {
  public marketAssets: MarketAssets = new MarketAssets([], [], []);
  isLoading: boolean = false;

  constructor(private dataService: DataService, public dialog: MatDialog) {}
  ngAfterViewInit(): void {
    this.fetchData(); //performance reasons
  }

  fetchData() {
    this.isLoading = true;
    let asset: Observable<any>[] = this.dataService.getMarketAssets();
    asset[0].subscribe((result) => {
      this.marketAssets = new MarketAssets(
        result,
        this.marketAssets.marketBonds,
        this.marketAssets.marketMfs
      );
    });
    asset[1].subscribe((result) => {
      this.marketAssets = new MarketAssets(
        this.marketAssets.marketStocks,
        result,
        this.marketAssets.marketMfs
      );
    });
    asset[2].subscribe((result) => {
      this.marketAssets = new MarketAssets(
        this.marketAssets.marketStocks,
        this.marketAssets.marketBonds,
        result
      );
    });
    this.isLoading = false;
  }

  ngOnInit(): void {}



  openDialog(data: any) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      minWidth: '400px',
    });
    dialogRef.componentInstance.infoDialogData = data;
    // refresh table data after dialog close
    dialogRef.afterClosed().subscribe(() => {
      this.fetchData();
    });
  }
}
