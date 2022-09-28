import { ComponentFixture, TestBed } from '@angular/core/testing';
import { BondHolding } from '../../../core/models/bond-holding';

import { BondTableComponent } from './bond-table.component';

describe('BondTableComponent', () => {
  let component: BondTableComponent;
  let fixture: ComponentFixture<BondTableComponent>;

  describe('when Stock Holding returns a non-empty list', () => {
    const mockStock: BondHolding[] =  [{
      name: 'Vanguard 500 Idx:Adm',
      code: 'VFIAX',
      quantity: 100,
      buy_price: 400.34,
      LTP: 419.68,
      asset_class: 'Government bonds',
    },
    {
      name: 'Fidelity 500 Index fund',
      code: 'FXAIX',
      quantity: 20,
      buy_price: 160.45,
      LTP: 157.76,
      asset_class: 'Government bonds',
    },
    {
      name: 'SPDR S&P 500 ETF',
      code: 'SPY',
      quantity: 12,
      buy_price: 439.45,
      LTP: 452.77,
      asset_class: 'Corporate bonds',
    }];
// Note that the argument to configureMockStockService() is an Observable 
// instead of a list. This allows other specs to pass the Observable
// that is returned by throwError() so we can test error handling.

    it(`will contain a table and has a "no Stock" message and 
        will not contain data error message`, async () => {

        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        const table = compiled.querySelector('app-reusable-table');

        expect(table.rows[0].cells[0].textContent).toBe('name');
        expect(table.rows[1].cells[0].textContent).toBe('Vanguard 500 Idx:Adm');
        expect(table.rows[2].cells[0].textContent).toBe('Fidelity 500 Index fund');
        expect(table.rows[3].cells[0].textContent).toBe('SPDR S&P 500 ETF');
    });
});

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BondTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BondTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
