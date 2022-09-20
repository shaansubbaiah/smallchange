import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { BuyTradeComponent } from './features/buy-trade/buy-trade.component';
import { HomePageComponent } from './features/home-page/home-page.component';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { MarketPlaceComponent } from './features/market-place/market-place.component';
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
  {
    path: 'portfolio',
    component: PortfolioComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'trade-history',
    component: TradeHistoryComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'market-place',
    component: MarketPlaceComponent,
    canActivate: [AuthGuard],
  },
  // { path: 'buy', component: BuyTradeComponent, canActivate: [AuthGuard] },
  // { path: 'sell', component: SellTradeComponent, canActivate: [AuthGuard] },
  { path: '**', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
