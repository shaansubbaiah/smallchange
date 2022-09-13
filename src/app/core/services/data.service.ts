import { Injectable } from '@angular/core';
import { dummy_data_order } from '../../core/models/mock-data';

@Injectable({
  providedIn: 'root',
})
export class DataService {
  constructor() {}

  getTradeHistory() {
    return dummy_data_order;
  }
}
