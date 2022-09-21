import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockTableOverviewComponent } from './stock-table-overview.component';

describe('StockTableOverviewComponent', () => {
  let component: StockTableOverviewComponent;
  let fixture: ComponentFixture<StockTableOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockTableOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockTableOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
