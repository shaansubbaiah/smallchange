import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BondHolding } from '../../../core/models/bond-holding';
import { Sort } from '@angular/material/sort';
import { CommonUtils } from 'src/app/utils';
@Component({
  selector: 'app-bond-table',
  templateUrl: './bond-table.component.html',
  styleUrls: ['./bond-table.component.scss'],
})
export class BondTableComponent implements AfterViewInit {
  invested_amount: number = 0;
  current_amount: number = 0;
  bonds: BondHolding[] = [];
  dataSource!: MatTableDataSource<BondHolding>;

  displayedColumns: string[] = [
    'name',
    'code',
    'quantity',
    'buy_price',
    'LTP',
    'asset_class',
  ];

  constructor(private dataService: DataService) {}

  @ViewChild(MatPaginator)
  paginator!: MatPaginator;

  ngAfterViewInit() {
    this.bonds = this.dataService.getBondHolding();
    this.dataSource = new MatTableDataSource<BondHolding>(this.bonds);
    this.dataSource.paginator = this.paginator;

    this.invested_amount = 0;
    this.current_amount = 0;
    for (var i = 0; i < this.bonds.length; i++) {
      this.invested_amount +=
        this.bonds[i].buy_price * this.bonds[i].quantity;
      this.current_amount += this.bonds[i].LTP * this.bonds[i].quantity;
    }
  }
  sortData($event: Sort) {
    let sortedData = CommonUtils.sortData<BondHolding>(this.bonds, $event);
    if (typeof(sortedData) !== 'undefined') this.dataSource = sortedData;
  }
}
