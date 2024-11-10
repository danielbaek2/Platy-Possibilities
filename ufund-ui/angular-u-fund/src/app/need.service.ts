import { Injectable } from '@angular/core';
import { Need } from './need';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, map, switchMap, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class NeedService {
  private needsUrl = 'http://localhost:8080/Cupboard';  // URL to web api http://localhost:8080/Cupboard

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { }

  getNeeds(): Observable<Need[]> {
    return this.http.get<Need[]>(this.needsUrl).pipe(
      tap(_ => ('fetched needs' )),
      catchError(this.handleError<Need[]>('getNeeds', [])));
  }

  getNeedNo404<Data>(id: number): Observable<Need> {
    const url = `${this.needsUrl}/?id=${id}`;
    return this.http.get<Need[]>(url).pipe(map(needs => needs[0]),tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          (`${outcome} need id=${id}`);
        }), catchError(this.handleError<Need>(`getNeed id=${id}`))
      );
  }

  getNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;
    return this.http.get<Need>(url).pipe(
      tap(_ => (`fetched need id=${id}`)),
      catchError(this.handleError<Need>(`getNeed id=${id}`)));
  }

  /** PUT: update the Need on the server */
  updateNeed(need: Need): Observable<any> {
    return this.http.put(this.needsUrl, need, this.httpOptions).pipe(
      tap(_ => (`updated need id=${need.id}`)),
      catchError(this.handleError<any>('updateNeed'))
    );
  }

  /** POST: add a new Need to the server */
  addNeed(need: Need): Observable<Need> {
    return this.getNeeds().pipe(
      map(needs => {
        const titleExists = needs.some(existingNeed => existingNeed.title === need.title);
        if (titleExists) {
          throw new Error(`Error: Need "${need.title}" already exists!`);
        }
        return need;
      }),
      switchMap(needToAdd =>
        this.http.post<Need>(this.needsUrl, needToAdd, this.httpOptions).pipe(
          tap((newNeed: Need) => (`added need w/ id=${newNeed.id}`)),
          catchError(this.handleError<Need>('addNeed'))
        )
      )
    );
  }

  /** DELETE: delete the Need from the server */
  deleteNeed(id: number): Observable<Need> {
    const url = `${this.needsUrl}/${id}`;
    return this.http.delete<Need>(url, this.httpOptions).pipe(
      tap(_ => (`deleted need id=${id}`)),
      catchError(this.handleError<Need>('deleteNeed')));
  }

  /* GET Needs whose title contains search term */
  searchNeeds(term: String): Observable<Need[]> {
    if (!term.trim()) {
      // if not search term, return empty need array.
      return of([]);
    }
    return this.http.get<Need[]>(`${this.needsUrl}/?need_title=${term}`).pipe(tap(x => x.length ?
        (`found needs matching "${term}"`):
        (`no needs matching "${term}"`)), catchError(this.handleError<Need[]>('searchNeeds', [])));
  }

  checkNeedIdExists(id: number): Observable<boolean> {
    return this.getNeed(id).pipe(
      map((need) => !!need),
      catchError(() => of(false))
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
