import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuyTradeComponent } from './features/buy-trade/buy-trade.component';
import { HomePageComponent } from './features/home-page/home-page.component';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { PageNotFoundComponent } from './features/page-not-found/page-not-found.component';
import { PortfolioComponent } from './features/portfolio/portfolio.component';
import { RegisterPageComponent } from './features/register-page/register-page.component';
import { SellTradeComponent } from './features/sell-trade/sell-trade.component';
import { TradeHistoryComponent } from './features/trade-history/trade-history.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomePageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  { path: 'portfolio', component: PortfolioComponent },
  { path: 'trade-history', component: TradeHistoryComponent },
  { path: 'buy', component: BuyTradeComponent },
  { path: 'sell', component: SellTradeComponent },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
