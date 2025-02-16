export interface User {
  name: string;
  email?: string;
  username?: string;
}

export interface UserOnlyName extends Omit<Omit<User, "name">, "email"> {//Если обычно у нас ковариантность, то тут контравариантность.. Наверное

}
