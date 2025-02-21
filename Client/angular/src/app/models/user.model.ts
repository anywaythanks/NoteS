export class User {
  readonly type = 'user';
  name: string;
  email: string;
  username: string;

  constructor(name: string, email: string, username: string) {
    this.name = name;
    this.email = email;
    this.username = username;
  }
}

/** Если обычно у нас ковариантность,
 то тут контравариантность.. Наверное**/
export interface UserOnlyName extends Omit<Omit<User, "name">, "email"> {

}


export class Anonymous {
  readonly type = "anonymous";
}
