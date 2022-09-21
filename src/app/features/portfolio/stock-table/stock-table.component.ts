import {
  AfterViewInit,
  Component,
  Input,
  OnInit,
  ViewChild,
} from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { StockHolding } from 'src/app/core/models/stock-holding';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from 'src/app/utils';
import { MatDialog } from '@angular/material/dialog';
import { StockTableDialogComponent } from './stock-table-dialog/stock-table-dialog.component';

@Component({
  selector: 'app-stock-table',
  templateUrl: './stock-table.component.html',
  styleUrls: ['./stock-table.component.scss'],
})
export class StockTableComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<StockHolding> = new MatTableDataSource();
  searchText: any;
  invested_amount: number = 0;
  current_amount: number = 0;

  displayedColumns: string[] = [
    'name',
    'code',
    'buy_price',
    'LTP',
    'quantity',
    'asset_class',
  ];

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {
    this.invested_amount = 0;
    this.current_amount = 0;

    for (var i = 0; i < this.holdings.length; i++) {
      this.invested_amount +=
        this.holdings[i].buy_price * this.holdings[i].quantity;
      this.current_amount += this.holdings[i].LTP * this.holdings[i].quantity;
    }
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  @Input()
  holdings!: StockHolding[];

  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource<StockHolding>(this.holdings);
    this.dataSource.paginator = this.paginator;
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<StockHolding>(this.holdings, $event);
    if (typeof sortedData !== 'undefined') this.dataSource = sortedData;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim();
  }

  onRowClick(row: any) {
    console.log(row);
    this.openDialog(row);
  }

  openDialog(data: any) {
    const dialogRef = this.dialog.open(StockTableDialogComponent);
    dialogRef.componentInstance.data = data;

    dialogRef.afterClosed().subscribe((result) => {
      console.log(`Dialog result: ${result}`);
    });
  }
}
