import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/internal/Observable';
import { environment } from 'src/environments/environment';
import { item } from '../models/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {
  private url = "item";
  constructor(private http: HttpClient) { }

  public getItems() : Observable<item[]> {
    return this.http.get<item[]>(`${environment.apiUrl}/${this.url}`);
  }
}
