import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StockTableDialogComponent } from './stock-table-dialog.component';

describe('StockTableDialogComponent', () => {
  let component: StockTableDialogComponent;
  let fixture: ComponentFixture<StockTableDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ StockTableDialogComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(StockTableDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
