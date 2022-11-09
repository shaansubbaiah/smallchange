import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { CommonUtils } from 'src/app/utils';

@Component({
  selector: 'app-refresh-button',
  templateUrl: './refresh-button.component.html',
  styleUrls: ['./refresh-button.component.scss'],
})
export class RefreshButtonComponent implements OnInit {
  @Input() isLoading: boolean = false;
  @Output() refreshEvent = new EventEmitter();
  lastRefreshedTimeString: string = '';
  lastRefreshedTime: number = Date.now();

  polling = setInterval(() => {
    this.lastRefreshedTimeString = CommonUtils.getTime(this.lastRefreshedTime);
  }, 1000);

  ngOnInit(): void {}

  onClick() {
    this.refreshEvent.emit(null);
  }

  ngOnDestroy(): void {
    clearInterval(this.polling);
  }
}
