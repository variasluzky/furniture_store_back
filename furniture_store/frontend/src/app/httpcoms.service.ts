import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HttpcomsService {

  constructor(private http: HttpClient) { }

  getData(): Observable<any> {
    return this.http.get('https://dummy.restapiexample.com/api/v1/employees/');
  }

  // getDataById( id: string ): Observable<any> {
    
  // }

  postData() {}

  deleteData() {}

  updateData() {}


}
