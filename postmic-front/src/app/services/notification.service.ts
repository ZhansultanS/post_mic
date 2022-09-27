import {Injectable} from '@angular/core';
import {MatSnackBar} from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor(private snackBar: MatSnackBar) {
  }

  show(message: string) {
    this.snackBar.open(message, 'Close', {
        duration: 3 * 1000,
        horizontalPosition: 'end',
        verticalPosition: 'top'
      }
    )
  }
}
