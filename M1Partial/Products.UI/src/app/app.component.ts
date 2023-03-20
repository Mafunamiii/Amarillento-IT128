import { Component } from '@angular/core';
import { item } from './models/item';
import { ItemService } from './services/item.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Products.UI';
  items: item[] = [];

  constructor (private ItemService: ItemService) {}

  ngOnInit(): void {
    this.items = this.ItemService.getItems();
    console.log(this.items);
  }
}

