import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { startWith, map, Observable, EMPTY, filter, zip, combineLatest, merge } from 'rxjs';
import { BondList } from 'src/app/core/models/bond-list';
import { bond_asset_classes, stock_asset_classes } from 'src/app/core/models/mock-data';
import { main_index_stocks, small_cap_company_stocks, international_market_stocks } from "src/app/core/models/mock-data";
import { corporate_bonds, government_bonds } from "src/app/core/models/mock-data";
import { StockList } from 'src/app/core/models/stock-list';

@Component({
  selector: 'app-sell-trade',
  templateUrl: './sell-trade.component.html',
  styleUrls: ['./sell-trade.component.scss']
})
export class SellTradeComponent implements OnInit {

  assetClass: string = '';
  assetGroups = [
    {name: 'Stocks', assetClasses: stock_asset_classes},
    {name: 'Bonds', assetClasses: bond_asset_classes},
  ];

  filteredOptions!: Observable<any[]>;

  assetClassStockMap: Map<string, StockList[]> = new Map();
  assetClassBondMap: Map<string, BondList[]> = new Map();

  sellTradeGrp: FormGroup;

  constructor(private fb : FormBuilder) {

    this.sellTradeGrp = this.fb.group({
      cbxAssetClass: ['', [Validators.required]],
      txtCompany: ['', [Validators.required]]
    }
    );
	  this.assetClassStockMap.set('main_index_stocks', main_index_stocks);
    this.assetClassStockMap.set('small_cap_company_stocks', small_cap_company_stocks);
    this.assetClassStockMap.set('international_market_stocks', international_market_stocks);

    this.assetClassBondMap.set('corporate_bonds', corporate_bonds);
    this.assetClassBondMap.set('government_bonds', government_bonds);

  }

  ngOnInit(): void {
    let o1 = this.sellTradeGrp.get('cbxAssetClass')?.valueChanges.pipe(
      startWith(''),
      map(value => {
        if (this.assetClass.includes('stock'))
          return this.getDisplayData(this.assetClassStockMap.get(this.assetClass));
        else
          return this.getDisplayData(this.assetClassBondMap.get(this.assetClass));
      }),
    ) || EMPTY;

    let o2 = this.sellTradeGrp.get('txtCompany')?.valueChanges.pipe(
      startWith(''),
      map(value => {
        console.log('o2, value:"' + value + '"');
        if (this.assetClass.includes('stock'))
          return this._filterStocks(value);
        else
          return this._filterBonds(value);
      }),
    ) || EMPTY;

    this.filteredOptions = merge(o1, o2);
  }

  private _filterStocks(value: string): any[] {
    if (value === null || typeof(value) === 'undefined' || value.length === 0) return this.getDisplayData(this.assetClassStockMap.get(this.assetClass));
    const filterValue = value.toLowerCase();

    let filteredList = this.assetClassStockMap.get(this.assetClass)?.filter(option => {
      return (option.name.toLowerCase().includes(filterValue) || option.code.toLowerCase().includes(filterValue))
    });
    return this.getDisplayData(filteredList);

  }

  private _filterBonds(value: string): any[] {
    if (value === null || typeof(value) === 'undefined' || value.length === 0) return this.getDisplayData(this.assetClassBondMap.get(this.assetClass));
    const filterValue = value.toLowerCase();
    let filteredList = this.assetClassBondMap.get(this.assetClass)?.filter(option => (option.name.toLowerCase().includes(filterValue) || option.code.toLowerCase().includes(filterValue)));

    return this.getDisplayData(filteredList);
  }

  getDisplayData(rawData: BondList[] | StockList[] | undefined): any[] {
    let toReturn = [];
      if (typeof(rawData) === 'undefined' || rawData.length === 0) return [{displayName: '', item: null}];

      for (let item of rawData)
        toReturn.push({
          displayName: item.name + '(' + item.code + ')',
          companyItem: item
        });
      console.log('toReturn:');
      console.log(toReturn);
      return toReturn;
  }
}
