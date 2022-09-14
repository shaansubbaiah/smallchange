import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MfTableComponent } from './mf-table.component';

describe('MfTableComponent', () => {
  let component: MfTableComponent;
  let fixture: ComponentFixture<MfTableComponent>;

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
