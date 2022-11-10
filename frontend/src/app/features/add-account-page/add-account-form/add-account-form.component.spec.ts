import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAccountPageComponent } from './add-account-page.component';

describe('AddAccountPageComponent', () => {
  let component: AddAccountPageComponent;
  let fixture: ComponentFixture<AddAccountPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddAccountPageComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAccountPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
