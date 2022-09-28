import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockTableComponent } from './stock-table.component';
import { StockHolding } from 'src/app/core/models/stock-holding';

describe('StockTableComponent', () => {
  let component: StockTableComponent;
  let fixture: ComponentFixture<StockTableComponent>;

  describe('when Stock Holding returns a non-empty list', () => {
    const mockStock: StockHolding[] =  [{
      name: 'Apple Inc',
      code: 'AAPL',
      quantity: 100,
      buy_price: 155.34,
      LTP: 154.29,
      asset_class: 'Main index stocks',
    },
    {
      name: 'Tesla Inc',
      code: 'TSLA',
      quantity: 20,
      buy_price: 657.65,
      LTP: 733.8,
      asset_class: 'Main index stocks',
    },
    {
      name: 'Amazon.com Inc',
      code: 'AMZN',
      quantity: 12,
      buy_price: 2354.34,
      LTP: 3471.2,
      asset_class: 'Main index stocks',
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
        expect(table.rows[1].cells[0].textContent).toBe('Apple Inc');
        expect(table.rows[2].cells[0].textContent).toBe('Tesla Inc');
        expect(table.rows[3].cells[0].textContent).toBe('Amazon.com Inc');
    });
});
 
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
