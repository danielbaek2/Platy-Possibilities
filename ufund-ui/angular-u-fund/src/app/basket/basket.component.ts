import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { Helper } from '../helper';
import { NEEDS } from '../mock-needs';
import { MessageService } from '../message.service';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {
  @Input() fundingBasket: Need[] = []
  @Input() username!: string // reecive username as an input from the helper component
  constructor(private helperService: HelperService, private route: ActivatedRoute){}

  ngOnInit(): void {
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.username = result
    }
  }
  
  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need)
    this.helperService.removeNeedFromBasket(need.id, this.username).subscribe();
  }
}
