import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { SharedModule } from './shared/shared.module';
import { CoreModule } from './core/core.module';
import { FeaturesModule } from './features/features.module';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BuyTradeComponent } from './pages/buy-trade/buy-trade.component';
import { SellTradeComponent } from './pages/sell-trade/sell-trade.component';
import { PortfolioComponent } from './pages/portfolio/portfolio.component';
import { TradeHistoryComponent } from './pages/trade-history/trade-history.component';

@NgModule({
  declarations: [AppComponent, BuyTradeComponent, SellTradeComponent, PortfolioComponent, TradeHistoryComponent],
  imports: [
    CoreModule,
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    SharedModule,
    FeaturesModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
