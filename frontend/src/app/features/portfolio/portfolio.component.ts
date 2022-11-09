import { Component, OnInit } from '@angular/core';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/info-dialog/info-dialog.component';
import { Observable } from 'rxjs';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss'],
})
export class PortfolioComponent implements OnInit {

  public userPortfolio: UserPortfolio = new UserPortfolio([], [], []);
  isLoading: boolean = false;
  lastRefreshedTimeString: string = '';
  lastRefreshedTime : number = Date.now();

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  private timer: ReturnType<typeof setInterval> = setInterval(() => {
    this.lastRefreshedTimeString = CommonUtils.getTime(this.lastRefreshedTime);
  }, 1000);

  refreshPortfolio() {
    this.ngOnInit();
    console.log('called!');
  }

  ngOnInit(): void {
    this.isLoading = true;

    let portfolio : Observable<any> = this.dataService.getPortfolio();
    portfolio.subscribe(result => {
      this.userPortfolio = new UserPortfolio(result.stocks, result.bonds, result.mfs);
      console.log(result);
      this.isLoading = false;
    });
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      minWidth: '400px',
    });
    dialogRef.componentInstance.infoDialogData = data;
  }
}
