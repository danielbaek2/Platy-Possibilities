import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { User } from './user';


@Injectable({ providedIn: 'root' })
export class UserService {

  private usersUrl = 'http://localhost:8080/users';  // URL to web api
  message: string = '';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient) { }

  /** GET heroes from the server */
  getUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.usersUrl)
      .pipe(
        catchError(this.handleError<User[]>('getUsers', []))
      );
  }

  /** GET hero by id. Return `undefined` when id not found */
  getUser(username: string): Observable<User> {
    const url = `${this.usersUrl}/?username=${username}`;
    return this.http.get<User>(url)
      .pipe(
        tap((_) =>
          console.log(`fetched user username=${username}`)),
        catchError(this.handleError<User>(`getUsers username=${username}`))
      );
  }

  /* GET heroes whose name contains search term */
  searchUsers(username: string): Observable<User[]> {
    if (!username.trim()) {
      return of([]);
    }
    return this.http.get<User[]>(`${this.usersUrl}/?name=${username}`).pipe(
      tap((_) => console.log(`found users matching "${username}"`)),
      catchError(this.handleError<User[]>('searchUsers', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new hero to the server */
  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap((newUser: User) => console.log(`added user w/ username=${newUser.username}`)),
      catchError(this.handleError<User>('addUser'))
    );
  }
/** DELETE: delete the hero from the server */
  deleteUser(username: string): Observable<User> {
    const url = `${this.usersUrl}/${username}`;

    return this.http.delete<User>(url, this.httpOptions).pipe(
      tap(_ => console.log(`deleted user username=${username}`)),
      catchError(this.handleError<User>('deleteUser'))
    );
  }

  /** PUT: update the hero on the server */
  updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap(_ => console.log(`updated user id=${user.username}`)),
      catchError(this.handleError<User>('updateUser'))
    );
  }

  loginUser(user: User): Observable<User> {
    return this.http.post<User>(this.usersUrl, user, this.httpOptions).pipe(
      tap(_ => console.log(`login user username= ${user.username}`)),
        catchError(this.handleError<User>('loginUser'))
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

      // TODO: better job of transforming error for user consumption
      this.message = (`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

}
