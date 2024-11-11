import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class MessageBoardService {
  private adminUrl = 'http://localhost:8080/Admin';
  private helperURL = 'http://localhost:8080/Helper';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  
  constructor(private http: HttpClient) { }

  getMessageBoard(): Observable<String[]> {
    return this.http.get<String[]>(`${this.adminUrl}/board`).pipe(
      tap(_ => ('fetched messages' )), 
      catchError(this.handleError<String[]>('getMessageBoard', [])));
  }

  deleteMessage(message: String): Observable<String> {
    const url = `${this.adminUrl}/board`;
    return this.http.delete<String>(url, { ...this.httpOptions, body: message }).pipe(
      tap(_ => (`deleted message=${message}`)),
      catchError(this.handleError<String>('deleteMessage'))
    );
  }  

  addMessage(message: String, username: String): Observable<String> {
    const url = `${this.helperURL}/${username}/board`;
    return this.http.put<String>(url, message, this.httpOptions).pipe(
      tap(_ => (`added message=${message}`)),
      catchError(this.handleError<String>('addMessage'))
    );
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
}
