import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../shared/shared.module';

import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatCardModule } from '@angular/material/card';
import { MatSelectModule } from '@angular/material/select';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatTabsModule } from '@angular/material/tabs';

import { LoginPageComponent } from './login-page/login-page.component';
import { LoginFormComponent } from './login-page/login-form/login-form.component';
import { HomePageComponent } from './home-page/home-page.component';
import { TradeHistoryComponent } from './trade-history/trade-history.component';
import { PortfolioComponent } from './portfolio/portfolio.component';
import { BuyTradeComponent } from './buy-trade/buy-trade.component';
import { SellTradeComponent } from './sell-trade/sell-trade.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';
import { StockTableComponent } from './portfolio/stock-table/stock-table.component';
import { MfTableComponent } from './portfolio/mf-table/mf-table.component';
import { BondTableComponent } from './portfolio/bond-table/bond-table.component';

@NgModule({
  declarations: [
    LoginPageComponent,
    LoginFormComponent,
    HomePageComponent,
    TradeHistoryComponent,
    PortfolioComponent,
    BuyTradeComponent,
    SellTradeComponent,
    PageNotFoundComponent,
    StockTableComponent,
    MfTableComponent,
    BondTableComponent,
  ],
  imports: [
    CommonModule,
    SharedModule,
    RouterModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCardModule,
    MatIconModule,
    MatCardModule,
    MatPaginatorModule,
    MatButtonModule,
    MatTableModule,
    MatSelectModule,
    MatAutocompleteModule,
    MatTabsModule,
  ],
  exports: [LoginPageComponent, HomePageComponent],
})
export class FeaturesModule {}
