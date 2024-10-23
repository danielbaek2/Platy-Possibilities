import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { HelperService } from '../helper.service';

@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: [ './helper.component.css' ]
})
export class HelperComponent implements OnInit {
  needs: Need[] = [];
  fundingBasket: Need[] = [];
  username: string = "helper"

  constructor(private needService: NeedService, private helperService: HelperService) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs.slice(1, 5));
  }

  addNeedToBasket(need: Need): void{
    this.helperService.addNeedToBasket(need, this.username).subscribe(need => {this.fundingBasket.push(need);});
  }

  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need)
    this.helperService.removeNeedFromBasket(need, this.username).subscribe();
  }

  getBasket(): void{
    this.helperService.getBasket(this.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket.slice());
  }
}