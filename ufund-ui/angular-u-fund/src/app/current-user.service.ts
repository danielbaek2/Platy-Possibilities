import { Injectable } from "@angular/core";
import { User } from "./user";
import { HttpClient, HttpHeaders } from "@angular/common/http";


@Injectable({ providedIn: 'root' })
export class CurrentUserService {

  user: string = "currentUser"

  constructor() { }
    public saveCurrentUser(user: User) {
      localStorage.setItem(this.user, JSON.stringify(user));}

  public alreadyCurrentUser(): boolean {
    return localStorage.getItem('currentUser') !== null;
  }

  public getCurrentUser():User | null {
    const user = localStorage.getItem(this.user);
    if (user) {
      return JSON.parse(this.user);
    }
    return null;
  }
  public clearCurrentUser():void {
    localStorage.removeItem(this.user);
  }
}
