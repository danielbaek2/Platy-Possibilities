export class User {
  username: string;
  admin: boolean;

  constructor(username: string, admin: boolean) {
    this.username = username;
    this.admin = admin;
  }
}
