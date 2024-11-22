import { Component, inject, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from "@angular/router";
import { CurrentUserService } from '../current-user.service';
import { HelperService } from '../helper.service';
import { MessageBoardService } from '../message-board.service';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { User } from '../user';

/**
 * Helper Component that provides functionality for viewing and adding needs to the funding basket
 */
@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: [ './helper.component.css' ]
})
export class HelperComponent implements OnInit {
  needs: Need[] = [];
  selectedNeeds: Need[] = [];
  fundingBasket: Need[] = [];
  user!: User;
  addedNeedMessage: String = "";
  currentUserService = inject(CurrentUserService);
  router = new Router;


  constructor(private needService: NeedService, private helperService: HelperService,
    private route: ActivatedRoute, private messageBoardService: MessageBoardService) {}

  /**
   * Loads the needs cupboard on initialization 
   */
  ngOnInit(): void {
    this.getNeeds();
    this.currentUserService.currentUser$.subscribe((user) => {
      if (user) {
        this.user = user;
        this.getBasket(); 
      }
    });
  }

  /**
   * Retrieves the list of needs from the need service
   */
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  /**
   * Retrieves the funding basket from the helper service
   * Updates the funding basket property with the retrieved data
   */
  getBasket(): void{
    this.helperService.getBasket(this.user.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }

  /**
   * Adds the given need to the list of selected needs once selected
   * @param need The selected need
   */
  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  /**
   * Adds all selected needs to the funding basket
   */
  addMultipleToBasket(): void{
    this.selectedNeeds.forEach(need => this.addNeedToBasket(need));
    this.addedNeedMessage = 'Item(s) have been added to the basket!';
  }

  /**
   * Adds a given need to the funding basket
   * @param need The need to be added
   */
  addNeedToBasket(need: Need): void{
      this.helperService.addNeedToBasket(need, this.user.username).subscribe(need => {this.fundingBasket.push(need);}); 
      this.addedNeedMessage = 'Item(s) have been added to the basket!';
  }

  /**
   * Assigns the user property with the given User object
   * @param new_user The new user object
   */
  setUser(new_user: User): void{
    this.user = new_user;
  }

  /**
   * Adds a given message to the message board
   * @param message The message to be added
   */
  addMessage(message: String): void {
    this.messageBoardService.addMessage(message, this.user.username).subscribe();
  }
}
