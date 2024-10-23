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

  constructor(private needService: NeedService, private helperService: HelperService) { }

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs.slice(1, 5));
  }

  addNeedToBasket(): void{
    //this.helperService.addNeedToBasket();
  }

  removeNeedFromBasket(): void{
    //this.helperService.removeNeedFromBasket();
  }

  getBasket(): void{
    this.helperService.getBasket();
  }
}