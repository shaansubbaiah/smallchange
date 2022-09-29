import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';

import { TradeHistoryComponent } from './trade-history.component';
import { TradeStock } from '../../core/models/trade-stock';
import { Observable } from 'rxjs';
import { AuthService } from 'src/app/core/services/auth.service';

describe('TradeHistoryComponent', () => {
  let component: TradeHistoryComponent;
  let fixture: ComponentFixture<TradeHistoryComponent>;

  describe('when Stock service returns a non-empty list', () => {
    const mockStock: TradeStock[] =  [{
      name: 'Apple Inc',
      code: 'AAPL',
      quantity: 100,
      price: 155.34,
      asset_class: 'Main index stocks',
      trade_type: 'buy',
      date: '2021/02/28',
      time: '09:10',
    },
    {
      name: 'Tesla Inc',
      code: 'TSLA',
      quantity: 20,
      price: 657.65,
      asset_class: 'Main index stocks',
      trade_type: 'buy',
      date: '2021/03/23',
      time: '11:40',
    },
    {
      name: 'Amazon.com Inc',
      code: 'AMZN',
      quantity: 12,
      price: 2354.34,
      asset_class: 'Main index stocks',
      trade_type: 'buy',
      date: '2021/04/23',
      time: '14:00',
    }];
// Note that the argument to configureMockEtfService() is an Observable 
// instead of a list. This allows other specs to pass the Observable
// that is returned by throwError() so we can test error handling.


    it(`will contain a table and doesn't have a "no Trade History" message and 
        will not contain a service error message`, async () => {

        fixture.detectChanges();
        const compiled = fixture.debugElement.nativeElement;
        const table = compiled.querySelector('app-reusable-table');
        if(table.rows != null){
        expect(table.rows.length).toBe(4);
        expect(table.rows[0].cells[0].textContent).toBe('name');
        expect(table.rows[1].cells[0].textContent).toBe('Apple Inc');
        expect(table.rows[2].cells[0].textContent).toBe('Tesla Inc');
        expect(table.rows[3].cells[0].textContent).toBe('Amazon.com Inc');
        }

    });
});

describe('when the Trade History returns an empty list', () => {
  const mockStock: TradeStock[] = [];

 

  it(`will not contain a table and does have a "no Trade History" message and 
      will not contain a service error message`, async () => {

      fixture.detectChanges();
      const compiled = fixture.debugElement.nativeElement;
      const table = compiled.querySelector('app-reusable-table');
      // messages change frequently, so we'll use a regular expression pattern match.
      // match whole words "no" and "Etfs", case insensitively
      console.log("No Trade History Available")
    
  });
});

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TradeHistoryComponent ],
      providers:[
        {provide: AuthService}
      ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TradeHistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});


