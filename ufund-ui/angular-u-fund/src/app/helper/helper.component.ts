import { Component, inject, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';
import { HELPER } from '../mock-helper';
import { User } from '../user';
import { MessageBoardService } from '../message-board.service';
import { CurrentUserService } from '../current-user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: [ './helper.component.css' ]
})
export class HelperComponent implements OnInit {
  needs: Need[] = [];
  selectedNeeds: Need[] = [];
  fundingBasket: Need[] = [];
  
  user: User = HELPER.user; // temporary hardcoded value
  currentUserService = inject(CurrentUserService);
  router = new Router;


  constructor(private needService: NeedService, private helperService: HelperService,
    private route: ActivatedRoute, private messageBoardService: MessageBoardService) {}

  ngOnInit(): void {
    this.getNeeds();
     this.currentUserService.currentUser$.subscribe((user) => {
       if (user) {
        this.user = user;
         this.getBasket(); // Load the basket for the user
     }
    });
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  addNeedToBasket(need: Need): void {
      this.helperService.addNeedToBasket(need, this.user.username).subscribe(need => {this.fundingBasket.push(need);}); 
  }

  removeNeedFromBasket(need: Need): void {
    this.fundingBasket = this.fundingBasket.filter(n => n !== need)
    this.helperService.removeNeedFromBasket(need, this.user.username).subscribe();
  }

  getBasket(): void{
    this.helperService.getBasket(this.user.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }

  setUser(new_user: User): void{
    this.user = new_user;
  }

  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  addMultileToBasket(): void{
    this.selectedNeeds.forEach(need => this.addNeedToBasket(need))
  }

  addMessage(message: String): void {
    this.messageBoardService.addMessage(message, this.user.username).subscribe();
  }
}
