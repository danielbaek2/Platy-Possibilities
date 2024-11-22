import { Injectable } from "@angular/core";
import { Subject } from "rxjs";
import { BehaviorSubject } from 'rxjs';
import { User } from './user';

@Injectable({ providedIn: 'root' })
export class CurrentUserService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  currentUser$ = this.currentUserSubject.asObservable();
  onButtonClick = new Subject();

  updateCurrentUser(user: User) {
    this.currentUserSubject.next(user); // setting the user in this service to link helper and login
  }

  /**
   * Checks if the current user is admin based on the username
   * @returns True if the user is admin, false otherwise
   */
  isAdmin(): boolean {
    const user = this.currentUserSubject.getValue();
    return user?.username.toLowerCase() === 'admin';
  }
}
