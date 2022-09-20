import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MarketMfTableComponent } from './market-mf-table.component';

describe('MarketMfTableComponent', () => {
  let component: MarketMfTableComponent;
  let fixture: ComponentFixture<MarketMfTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MarketMfTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MarketMfTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
