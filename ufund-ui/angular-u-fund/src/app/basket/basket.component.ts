import { Component} from '@angular/core';
import { Need } from '../need';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-basket',
  templateUrl: './basket.component.html',
  styleUrl: './basket.component.css'
})
export class BasketComponent {
  private selectedNeeds: Need[] = [];
  private clicked = true;
  fundingBasket!: Need[];
  username!: string;
  buttonText = 'Select all';

  constructor(private helperService: HelperService, private route: ActivatedRoute){}

  ngOnInit(): void {
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.username = result;
    }
    this.helperService.getBasket(this.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }
  
 
  onClick(): void{
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

  isEmpty(element: string): boolean{
    const isEmpty = this.fundingBasket.length === 0;
    (document.getElementById(element) as HTMLInputElement).disabled = isEmpty; //disable the button if the funding basket is empty
    return isEmpty;
  }

  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need);
    this.helperService.removeNeedFromBasket(need, this.username).subscribe();
  }

  removeMultipleFromBasket(): void{
    this.selectedNeeds.forEach(need => this.removeNeedFromBasket(need));
  }
}
