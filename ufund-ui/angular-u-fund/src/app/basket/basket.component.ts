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
  selectedNeeds: Need[] = [];
  constructor(private helperService: HelperService, private route: ActivatedRoute){}

  ngOnInit(): void {
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.username = result;
    }
    this.helperService.getBasket(this.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }
  
  clicked = true;
  buttonText = 'Select all'
  onClick(element: any): void{
    const checkboxes = document.querySelectorAll('ul li label input[type="checkbox"]') as NodeListOf<HTMLInputElement>;
    this.buttonText = this.buttonText === 'Select all' ? 'Deselect all' : 'Select all';

    if (this.clicked){
        this.clicked = false;
        this.selectAll(checkboxes);
      } else{
        this.clicked = true;
        this.deselectAll(checkboxes);
      }
  }

  selectAll(checkboxes: NodeListOf<HTMLInputElement>): void{
    this.selectedNeeds = this.fundingBasket;
    checkboxes.forEach(checkbox => checkbox.checked = true)
  }

  deselectAll(checkboxes: NodeListOf<HTMLInputElement>): void{
    this.selectedNeeds = [];    
    checkboxes.forEach(checkbox => checkbox.checked = false)
  }

  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need);
    this.helperService.removeNeedFromBasket(need, this.username).subscribe();
  }

  removeMultipleFromBasket(): void{
    this.selectedNeeds.forEach(need => this.removeNeedFromBasket(need));
  }
}
