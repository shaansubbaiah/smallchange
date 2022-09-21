import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  Input,
} from '@angular/core';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from 'src/app/utils';
// import { MatDialog } from '@angular/material/dialog';
// import { StockTableDialogComponent } from './stock-table-dialog/stock-table-dialog.component';

@Component({
  selector: 'app-reusable-table',
  templateUrl: './reusable-table.component.html',
  styleUrls: ['./reusable-table.component.scss'],
})
export class ReusableTableComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<any> = new MatTableDataSource();
  searchText: any;
  displayedColumns: string[] = [];

  @Input() tableRows: any[] = [];
  @Input() tableColumns: any[] = [];

  constructor() {}

  ngOnInit(): void {
    this.tableColumns.forEach((e) => {
      this.displayedColumns.push(e.name);
    });
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource<any>(this.tableRows);
    this.dataSource.paginator = this.paginator;
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<any>(this.tableRows, $event);
    if (typeof sortedData !== 'undefined') this.dataSource = sortedData;
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim();
  }

  onRowClick(row: any) {
    console.log(row);
  }
}
