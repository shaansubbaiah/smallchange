import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketBondTableComponent } from './market-bond-table.component';

describe('MarketBondTableComponent', () => {
  let component: MarketBondTableComponent;
  let fixture: ComponentFixture<MarketBondTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarketBondTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketBondTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
