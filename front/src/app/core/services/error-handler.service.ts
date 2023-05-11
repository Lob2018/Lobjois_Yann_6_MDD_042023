import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  constructor(private snackBar: MatSnackBar) {}

  showSnackBarError(msg: string) {
    this.snackBar.open(msg, '', {
      duration: 7000,
      panelClass: ['multiline-snackbar'],
    });
  }
  /**
   * Deals with :
   * 1. server backend reject the request with HTTP error response and status code
   * 2. error on the client side (network or RxJS operator exception thrown) with status 0 and a ProgressEvent object
   * Note : before, don't forgert to set empty value for the observable, it can be useful to end loading state and let the user know something went wrong
   * @param error
   * @returns
   */
  handleError(error: HttpErrorResponse) {
    let msg =
      'Backend returned code ' + error.status + '\n' + error.error?.message;
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
      msg = 'A client-side or network error occurred';
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    this.showSnackBarError(msg);
    // then return an observable with a user-facing error message.
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
