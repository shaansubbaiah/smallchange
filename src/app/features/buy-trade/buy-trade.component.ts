import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { startWith, map, Observable, EMPTY } from 'rxjs';
import { BondList } from 'src/app/core/models/bond-list';
import { bond_asset_classes, stock_asset_classes } from 'src/app/core/models/mock-data';
import { main_index_stocks, small_cap_company_stocks, international_market_stocks } from "src/app/core/models/mock-data";
import { corporate_bonds, government_bonds } from "src/app/core/models/mock-data";
import { StockList } from 'src/app/core/models/stock-list';

@Component({
  selector: 'app-buy-trade',
  templateUrl: './buy-trade.component.html',
  styleUrls: ['./buy-trade.component.scss']
})
export class BuyTradeComponent implements OnInit {

  assetClass: string = '';
  assetGroups = [
    {name: 'Stocks', assetClasses: stock_asset_classes},
    {name: 'Bonds', assetClasses: bond_asset_classes},
  ];

  filteredOptions!: Observable<any[]>;

  assetClassStockMap: Map<string, StockList[]> = new Map();
  assetClassBondMap: Map<string, BondList[]> = new Map();

  buyTradeGrp: FormGroup;

  constructor(private fb : FormBuilder) {

    this.buyTradeGrp = this.fb.group({
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
    this.filteredOptions = this.buyTradeGrp.get('txtCompany')?.valueChanges.pipe(
      startWith(''),
      map(value => {
        let toReturn = this._filterStocks(value);
        if (toReturn.length === 0)
          return this._filterBonds(value);

        else return this._filterStocks(value);
      }),
    ) || EMPTY;
  }

  private _filterStocks(value: string): any[] {
    const filterValue = value.toLowerCase();

    let filteredList = this.assetClassStockMap.get(this.assetClass)?.filter(option => {
      return (option.name.toLowerCase().includes(filterValue) || option.code.toLowerCase().includes(filterValue))
    });
    let toReturn = [];
    if (typeof(filteredList) === 'undefined') return [{displayName: '', item: null}];

    for (let item of filteredList)
      toReturn.push({
        displayName: item.name + '(' + item.code + ')',
        companyItem: item
      });

    return toReturn;
  }

  private _filterBonds(value: string): any[] {
    const filterValue = value.toLowerCase();
    let filteredList = this.assetClassBondMap.get(this.assetClass)?.filter(option => (option.name.toLowerCase().includes(filterValue) || option.code.toLowerCase().includes(filterValue)));
    let toReturn = [];
    if (typeof(filteredList) === 'undefined') return [{displayName: '', item: null}];

    for (let item of filteredList)
      toReturn.push({
        displayName: item.name + '(' + item.code + ')',
        companyItem: item
      });

    return toReturn;
  }
}
