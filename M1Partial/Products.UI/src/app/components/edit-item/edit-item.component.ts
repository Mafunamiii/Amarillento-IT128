import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { item } from 'src/app/models/item';
import { ItemService } from 'src/app/services/item.service';

@Component({
  selector: 'app-edit-item',
  templateUrl: './edit-item.component.html',
  styleUrls: ['./edit-item.component.css']
})
export class EditItemComponent implements OnInit{
  @Input() item? : item;
  @Output() itemsUpdated = new EventEmitter<item[]>();


  constructor (private itemService : ItemService) {}

  ngOnInit() : void {}

  UpdateItem(item:item) {
    this.itemService.UpdateItem(item)
    .subscribe((item) => this.itemsUpdated.emit(item));
  }

  DeleteItem(item:item) {
    this.itemService.DeleteItem(item)
    .subscribe((item) => this.itemsUpdated.emit(item));
  }

  CreateItem(item:item) {
    this.itemService.CreateItem(item)
    .subscribe((item) => this.itemsUpdated.emit(item));
  }
  
}

