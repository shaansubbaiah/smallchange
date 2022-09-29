import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { SnackbarComponent } from 'src/app/shared/snackbar/snackbar.component';

interface AlertData {
  type: string;
  message: string;
}

@Injectable({
  providedIn: 'root',
})
export class AlertService {
  durationInSeconds = 3;

  constructor(private _snackBar: MatSnackBar) {}

  open(data: AlertData) {
    this._snackBar.openFromComponent(SnackbarComponent, {
      duration: this.durationInSeconds * 1000,
      data: data,
      panelClass: ['alert-bg'],
    });
  }
}
