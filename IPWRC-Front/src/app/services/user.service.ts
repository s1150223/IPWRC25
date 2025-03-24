import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface User {
  id?: number;
  username: string;
  password: string;
  role: 'ROLE_USER' | 'ROLE_ADMIN';
}

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiUrl = 'https://ipwrc25back.onrender.com/api/users';

  constructor(private http: HttpClient) {
  }

  createUser(user: User): Observable<User> {
    return this.http.post<User>(this.apiUrl, user);
  }

  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.apiUrl);
  }

}
