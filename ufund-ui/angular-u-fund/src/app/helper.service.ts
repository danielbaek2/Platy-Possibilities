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
  private helperURL = 'http://localhost:4200/Helper';
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private messageService: MessageService, private http: HttpClient) { }
  fundingBasket: Need[] = [];
  
  /**
   * GET basket given username
   * @param username 
   * @returns 
   */
  getBasket(username: string): Observable<Need[]>{
    return this.http.get<Need[]>(`${this.helperURL}/${username}/basket`).pipe(
      tap(_ => this.log('fetched basket' )), 
      catchError(this.handleError<Need[]>('getBasket', [])));
  } 

  addNeedToBasket(need: Need, username: string): Observable<Need>{
    return this.http.post<Need>(`${this.helperURL}/${username}`, need, this.httpOptions).pipe( // should call server side
      tap((newNeed: Need) => this.log(`added need to funding basket w/ id=${newNeed.id}`)),
      catchError(this.handleError<Need>('addNeedToBasket'))
    );
  }

  removeNeedFromBasket(need: Need, username: string): Observable<Need>{
    const index = this.fundingBasket.indexOf(need)
    if (index > -1){
      this.fundingBasket.splice(index, 1);
    }
    return this.http.delete<Need>(`${this.helperURL}/${username}?basket/${need.id}`, this.httpOptions).pipe( // should call server side
      tap(_ => this.log(`removed need title=${need.title}`))
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
}
