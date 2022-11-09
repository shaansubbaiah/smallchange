import { Component, OnInit } from '@angular/core';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/info-dialog/info-dialog.component';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss'],
})
export class PortfolioComponent implements OnInit {
  public userPortfolio: UserPortfolio = new UserPortfolio([], [], []);
  isLoading: boolean = false;

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  fetchData() {
    this.isLoading = true;
    this.dataService.getPortfolio().subscribe((result) => {
      this.userPortfolio = new UserPortfolio(
        result.stocks,
        result.bonds,
        result.mfs
      );
      console.log(result);
      this.isLoading = false;
    });
  }

  ngOnInit(): void {
    this.fetchData();
  }

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
