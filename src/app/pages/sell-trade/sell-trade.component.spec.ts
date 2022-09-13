import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SellTradeComponent } from './sell-trade.component';

describe('SellTradeComponent', () => {
  let component: SellTradeComponent;
  let fixture: ComponentFixture<SellTradeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SellTradeComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SellTradeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
