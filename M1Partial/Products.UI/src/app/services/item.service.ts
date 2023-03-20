import { Injectable } from '@angular/core';
import { item } from '../models/item';

@Injectable({
  providedIn: 'root'
})
export class ItemService {

  constructor() { }

  public getItems() : item[] {
    let testItem = new item();
    testItem.id = 1;
    testItem.name = "TestItem";
    testItem.code = "00000001";
    testItem.brand= "Original";
    testItem.unitPrice= 100;

    return [testItem];
  }
}
