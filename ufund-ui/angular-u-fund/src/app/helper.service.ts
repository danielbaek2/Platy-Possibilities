import { Injectable } from '@angular/core';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Need } from './need';

import { MessageService } from './message.service';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HelperService {
  private helperURL = 'http://localhost:8080/Helper';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private messageService: MessageService, private http: HttpClient) { }
  
  /**
   * GET basket from the server based on a username
   * @param username The username of the user
   * @returns 
   */
  getBasket(username: string): Observable<Need[]>{
    return this.http.get<Need[]>(`${this.helperURL}/${username}/basket`).pipe(
      tap(_ => this.log('fetched basket' )), 
      catchError(this.handleError<Need[]>('getBasket', [])));
  } 

  /**
   * PUT a need in the basket in the server based on the username
   * @param need The need to add to the basket
   * @param username The username of the user
   * @returns 
   */
  addNeedToBasket(need: Need, username: string): Observable<Need>{
    return this.http.put<Need>(`${this.helperURL}/${username}?id=${need.id}`, need, this.httpOptions).pipe( // should call server side
      tap((newNeed: Need) => this.log(`added need to funding basket w/ id=${newNeed.id}`)),
      catchError(this.handleError<Need>('addNeedToBasket'))
    );
  }

  /**
   * DELETE (remove) a need from the basket on the server
   * @param need The need to remove from the basket
   * @param username The username of the user
   * @returns 
   */
  removeNeedFromBasket(need: Need, username: string): Observable<Need>{
    this.httpOptions.headers.append('Need', `${need}`)
    console.log(this.httpOptions)
    return this.http.delete<Need>(`${this.helperURL}/${username}/basket?id=${need.id}`, this.httpOptions).pipe( // should call server side
      tap(_ => this.log(`removed need id=${need.id}`))
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

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a HelperService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`HelperService: ${message}`);
  }

  checkoutBasket(username:string): Observable<Need[]>{
    let removedNeeds = this.http.delete<Need[]>(`${this.helperURL}/${username}/checkout`, this.httpOptions).pipe(
      tap(needs => needs.forEach(need => {
        this.log(`need removed: ${need.title}`);
        this.http.delete<Need>(`http://localhost:8080/Cupboard/${need.id}`,this.httpOptions).pipe(
          tap(need => this.log(`Removed need from cupboard: ${need.title}`))
        );
      })
    ))
    return removedNeeds
  }
}
