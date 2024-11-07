import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';
import { HELPER } from '../mock-helper';
import { User } from '../user';

@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: [ './helper.component.css' ]
})
export class HelperComponent implements OnInit {
  needs: Need[] = [];
  selectedNeeds: Need[] = [];
  fundingBasket: Need[] = [];
  user: User = HELPER.user // temporary hardcoded value

  constructor(private needService: NeedService, private helperService: HelperService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getNeeds();
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.user.username = result
    }
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
    this.selectedNeeds.forEach(need => this.addNeedToBasket)
  }

  addNeedToBasket(need: Need): void{
      this.helperService.addNeedToBasket(need, this.user.username).subscribe(need => {this.fundingBasket.push(need);}); 
  }

  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need)
    this.helperService.removeNeedFromBasket(need, this.user.username).subscribe();
  }
}