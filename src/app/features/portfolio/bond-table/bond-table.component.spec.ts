import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BondTableComponent } from './bond-table.component';

describe('BondTableComponent', () => {
  let component: BondTableComponent;
  let fixture: ComponentFixture<BondTableComponent>;

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
