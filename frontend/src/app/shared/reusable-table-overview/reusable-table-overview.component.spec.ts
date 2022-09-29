import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ReusableTableOverviewComponent } from './reusable-table-overview.component';

describe('ReusableTableOverviewComponent', () => {
  let component: ReusableTableOverviewComponent;
  let fixture: ComponentFixture<ReusableTableOverviewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ReusableTableOverviewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ReusableTableOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
