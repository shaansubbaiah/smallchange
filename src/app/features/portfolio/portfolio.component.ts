import { Component, Input, OnInit } from '@angular/core';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { PortfolioDialogComponent } from './portfolio-dialog/portfolio-dialog.component';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss'],
})
export class PortfolioComponent implements OnInit {
  public userPortfolio!: UserPortfolio;

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  ngOnInit(): void {
    this.userPortfolio = this.getUserPortfolio();
  }

  public getUserPortfolio(): UserPortfolio {
    return this.dataService.getPortfolio();
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(PortfolioDialogComponent);
    dialogRef.componentInstance.data = data;
  }
}
