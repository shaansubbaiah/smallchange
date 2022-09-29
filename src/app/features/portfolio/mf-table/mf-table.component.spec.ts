import { CdkRecycleRows } from '@angular/cdk/table';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MfHolding } from '../../../core/models/mf-holding';

import { MfTableComponent } from './mf-table.component';

describe('MfTableComponent', () => {
  let component: MfTableComponent;
  let fixture: ComponentFixture<MfTableComponent>;

  describe('when MF Holding returns a non-empty list', () => {
    const mockStock: MfHolding[] =  [{
      name: 'Vanguard 500',
      code: 'VFIAX',
      quantity: 100,
      buy_price: 400.34,
      LTP: 419.68,
    },
    {
      name: 'Fidelity 500 Index fund',
      code: 'FXAIX',
      quantity: 20,
      buy_price: 160.45,
      LTP: 157.76,
    },
    {
      name: 'SPDR S&P 500 ETF',
      code: 'SPY',
      quantity: 12,
      buy_price: 439.45,
      LTP: 452.77,
    }];


    it(`will contain a table and has a "no Mutual Fund" message and 
        will not contain data error message`, async () => {

        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        const table = compiled.querySelector('app-reusable-table');

        if(table.rows != null){
        expect(table.rows.length).toBe(4);
        expect(table.rows[0].cells[0].textContent).toBe('name');
        expect(table.rows[1].cells[0].textContent).toBe('Vanguard 500');
        expect(table.rows[2].cells[0].textContent).toBe('Fidelity 500 Index fund');
        expect(table.rows[3].cells[0].textContent).toBe('SPDR S&P 500 ETF');
        }
    });
});

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MfTableComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MfTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
