import { Component, OnInit } from '@angular/core';
import { UserPortfolio } from 'src/app/core/models/user-portfolio';
import { DataService } from 'src/app/core/services/data.service';
import { MatDialog } from '@angular/material/dialog';
import { InfoDialogComponent } from 'src/app/shared/info-dialog/info-dialog.component';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-portfolio',
  templateUrl: './portfolio.component.html',
  styleUrls: ['./portfolio.component.scss'],
})
export class PortfolioComponent implements OnInit {
  public userPortfolio!: UserPortfolio;

  constructor(private dataService: DataService, public dialog: MatDialog) {}

  ngOnInit(): void {
    let portfolio : Observable<any> = this.dataService.getPortfolio();
    portfolio.subscribe(result => {

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
