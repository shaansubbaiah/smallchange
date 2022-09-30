import { TestBed } from '@angular/core/testing';

import { BuySellService } from './buy-sell.service';

describe('BuySellService', () => {
  let service: BuySellService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(BuySellService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
