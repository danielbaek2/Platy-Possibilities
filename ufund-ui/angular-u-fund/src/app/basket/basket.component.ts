import { Component} from '@angular/core';
import { Need } from '../need';
import { MessageService } from '../message.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {
  fundingBasket!: Need[];
  username!: string;
  constructor(private helperService: HelperService, private route: ActivatedRoute){}

  ngOnInit(): void {
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.username = result
    }
    this.helperService.getBasket(this.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }
  
  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need)
    this.helperService.removeNeedFromBasket(need, this.username).subscribe();
  }

  checkoutBasket(): void{
    this.helperService.checkoutBasket(this.username).subscribe();
    this.fundingBasket = []
  }
}
