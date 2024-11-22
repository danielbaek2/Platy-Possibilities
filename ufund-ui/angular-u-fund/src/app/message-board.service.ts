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

  /**
   * GET message board from the server
   * @returns An observable to the list of strings (message board) retrieved
   */
  getMessageBoard(): Observable<String[]> {
    return this.http.get<String[]>(`${this.adminUrl}/board`).pipe(
      tap(_ => ('fetched messages' )), 
      catchError(this.handleError<String[]>('getMessageBoard', [])));
  }

  /**
   * DELETE a message from the message board on the server
   * @param message The message to be deleted
   * @returns An Observable of the string object (message) removed
   */
  deleteMessage(message: String): Observable<String> {
    const url = `${this.adminUrl}/board`;
    return this.http.delete<String>(url, { ...this.httpOptions, body: message }).pipe(
      tap(_ => (`deleted message=${message}`)),
      catchError(this.handleError<String>('deleteMessage'))
    );
  }  

  /**
   * DELETE a message to the message board on the server
   * @param message The message to add to the message board
   * @param username The username of the user
   * @returns An observable to the string message retrieved
   */
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
