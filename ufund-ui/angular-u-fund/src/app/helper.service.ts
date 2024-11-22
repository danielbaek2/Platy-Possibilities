import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Need } from './need';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  private helperURL = 'http://localhost:8080/Helper';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  /**
   * GET funding basket from the server based on a username
   * @param username The username of the user
   * @returns An observable to the list of needs (funding basket) retrieved
   */
  getBasket(username: string): Observable<Need[]> {
    return this.http.get<Need[]>(`${this.helperURL}/${username}/basket`).pipe(
      tap(_ => ('fetched basket')),
      catchError(this.handleError<Need[]>('getBasket', [])));
  }

  /**
   * PUT a need in the basket in the server based on the username
   * @param need The need to add to the basket
   * @param username The username of the user
   * @returns An observable to the need retrieved
   */
  addNeedToBasket(need: Need, username: string): Observable<Need> {
    return this.http.put<Need>(`${this.helperURL}/${username}?id=${need.id}`, need, this.httpOptions).pipe( // should call server side
      tap((newNeed: Need) => (`added need to funding basket w/ id=${newNeed.id}`)),
      catchError(this.handleError<Need>('addNeedToBasket'))
    );
  }

  /**
   * DELETE (remove) a need from the basket on the server
   * @param need The need to remove from the basket
   * @param username The username of the user
   * @returns An Observable to the need removed
   */
  removeNeedFromBasket(need: Need, username: string): Observable<Need> {
    this.httpOptions.headers.append('Need', `${need}`)
    console.log(this.httpOptions)
    return this.http.delete<Need>(`${this.helperURL}/${username}/basket?id=${need.id}`, this.httpOptions).pipe( // should call server side
      tap(_ => (`removed need id=${need.id}`))
    )
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /**
   * Checks out a user's funding basket
   * @param username The username of the user
   * @returns An observable to the list of needs (funding basket) after the needs have been checked out
   */
  checkoutBasket(username: string): Observable<Need[]> {
    return this.http.delete<Need[]>(`${this.helperURL}/${username}/checkout`, this.httpOptions).pipe(
      tap(needs => {needs.forEach(need => {
        (`need removed: ${need.title}`)
        const updatedNeed = {...need, quantity_funded: need.quantity};
          this.http.put<Need>(`http://localhost:8080/Cupboard`, updatedNeed, this.httpOptions).pipe(
            tap(updated => {(`Updated need to fully funded in Cupboard: ${updated.title}`);
            })).subscribe();
        });
      })
    );
  }
}
