import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { throwError } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ErrorHandlerService {
  constructor() {}

  /**
   * Deals with :
   * 1. server backend reject the request with HTTP error response and status code
   * 2. error on the client side (network or RxJS operator exception thrown) with status 0 and a ProgressEvent object
   * Note : before, don't forgert to set empty value for the observable, it can be useful to end loading state and let the user know something went wrong
   * @param error
   * @returns
   */
  handleError(error: HttpErrorResponse) {
    if (error.status === 0) {
      // A client-side or network error occurred. Handle it accordingly.
      console.error('An error occurred:', error.error);
    } else {
      // The backend returned an unsuccessful response code.
      // The response body may contain clues as to what went wrong.
      console.error(
        `Backend returned code ${error.status}, body was: `,
        error.error
      );
    }
    // then return an observable with a user-facing error message.
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
