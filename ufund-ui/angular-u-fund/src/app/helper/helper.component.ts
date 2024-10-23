import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { NeedService } from '../need.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';
import { NEEDS } from '../mock-needs';

@Component({
  selector: 'app-helper',
  templateUrl: './helper.component.html',
  styleUrls: [ './helper.component.css' ]
})
export class HelperComponent implements OnInit {
  needs: Need[] = [];
  fundingBasket: Need[] = NEEDS;
  username: string = "helper" // temporary hardcoded value 

  constructor(private needService: NeedService, private helperService: HelperService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  addNeedToBasket(need: Need): void{
    // username = this.route.snapshot.paramMap.get('username');
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