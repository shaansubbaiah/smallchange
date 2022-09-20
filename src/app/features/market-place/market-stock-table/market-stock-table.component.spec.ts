import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketStockTableComponent } from './market-stock-table.component';

describe('MarketStockTableComponent', () => {
  let component: MarketStockTableComponent;
  let fixture: ComponentFixture<MarketStockTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarketStockTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketStockTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
