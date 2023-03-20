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

  public UpdateItem(item: item) : Observable<item[]> {
    return this.http.put<item[]>(`${environment.apiUrl}/${this.url}`, item);
  }

  public CreateItem(item: item) : Observable<item[]> {
    return this.http.post<item[]>(`${environment.apiUrl}/${this.url}`, item);
  }

  public DeleteItem(item: item) : Observable<item[]> {
    return this.http.delete<item[]>(
      `${environment.apiUrl}/${this.url}/${item.id}`
    );
  }
}
