import { Component, AfterViewInit, ViewChild } from '@angular/core';
import { DataService } from 'src/app/core/services/data.service';

import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { BondHolding } from '../../../core/models/bond-holding';
@Component({
  selector: 'app-bond-table',
  templateUrl: './bond-table.component.html',
  styleUrls: ['./bond-table.component.scss'],
})
export class BondTableComponent implements AfterViewInit {
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
  }
}
