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

@Component({
  selector: 'app-reusable-table',
  templateUrl: './reusable-table.component.html',
  styleUrls: ['./reusable-table.component.scss'],
})
export class ReusableTableComponent implements OnInit, AfterViewInit {
  dataSource: MatTableDataSource<any> = new MatTableDataSource();
  displayedColumns: string[] = [];
  noResultsFound: boolean = false;

  @Input() tableRows: any[] = [];
  @Input() tableColumns: any[] = [];
  @Input() filterColumns: string[] = [];

  ngOnInit(): void {
    this.tableColumns.forEach((e) => {
      this.displayedColumns.push(e.name);
    });
    this.setFilterColumns();
  }

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource = new MatTableDataSource<any>(this.tableRows);
    this.dataSource.paginator = this.paginator;
    this.setFilterColumns();
  }

  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<any>(this.tableRows, $event);
    if (typeof sortedData !== 'undefined') {
      this.dataSource = sortedData;
      this.setFilterColumns();
    }
  }

  setFilterColumns() {
    if (this.filterColumns === null || this.filterColumns.length == 0) {
      this.filterColumns = [];
      console.log('using default sort');
      this.tableColumns.forEach(e => {
        this.filterColumns.push(e.name);
      });
    }
    this.dataSource.filterPredicate = (data: any, filter: string) => {
      let flg: boolean = false;
      for (let col of this.filterColumns) {
        flg = (data[col] !== null && String(data[col]).toLowerCase().includes(filter));
        if (flg) break;
      }

      return flg;
    };
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim();
    if (this.dataSource.filteredData.length == 0) {
      this.noResultsFound = true;
    } else {
      this.noResultsFound = false;
    }
  }

  onRowClick(row: any) {
    console.log(row);
  }
}
