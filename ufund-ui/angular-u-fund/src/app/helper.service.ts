import { Injectable } from '@angular/core';

import { Need } from './need';
import { MessageService } from './message.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HelperService {

  constructor(private messageService: MessageService, private http: HttpClient) { }
  fundingBasket: Need[] = [];

  getBasket(): Need[]{
    return this.fundingBasket; 
  } 

  addNeedToBasket(need: Need): void{
    this.fundingBasket.push(need)
  }

  removeNeedFromBasket(need: Need): void{
    const index = this.fundingBasket.indexOf(need)
    if (index > -1){
      this.fundingBasket.splice(index, 1);
    }
  }
}
