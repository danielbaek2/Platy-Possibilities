import { Component, inject, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';
import { User } from '../user';
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
  user!: User;
  currentUserService = inject(CurrentUserService);
  router = new Router;


  constructor(private needService: NeedService, private helperService: HelperService,
    private route: ActivatedRoute) {}

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

  getBasket(): void{
    this.helperService.getBasket(this.user.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }

  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  addMultileToBasket(): void{
    this.selectedNeeds.forEach(need => this.addNeedToBasket(need))
  }

  addNeedToBasket(need: Need): void{
      this.helperService.addNeedToBasket(need, this.user.username).subscribe(need => {this.fundingBasket.push(need);}); 
  }

  setUser(new_user: User): void{
    this.user = new_user;
  }
}

