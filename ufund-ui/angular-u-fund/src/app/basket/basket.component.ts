import { Component} from '@angular/core';
import { Need } from '../need';
import { HelperService } from '../helper.service';
import { ActivatedRoute } from '@angular/router';

/**
 * Basket Component that provides functionality for removing needs from the basket
 */
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

  /**
   * Retrieves and stores the user's username from the url
   * Populates the funding basket with needs on initialization
   */
  ngOnInit(): void {
    const result = this.route.snapshot.paramMap.get('username')?.toString();
    if (result != null){
      this.username = result;
    }
    this.helperService.getBasket(this.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }
  
  /**
   * Provides functionality for selecting and deselecting all checkboxes when the button is clicked
   */
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

  /**
   * Selects all checkboxes and adds the entire funding basket to the list of selected needs
   * @param checkboxes A NodeList of the checkboxes to be selected
   */
  selectAll(checkboxes: NodeListOf<HTMLInputElement>): void{
    this.selectedNeeds = this.fundingBasket;
    checkboxes.forEach(checkbox => checkbox.checked = true)
  }

  /**
   * Deselects all checkboxes and clears the list of selected needs 
   * @param checkboxes A NodeList of the checkboxes to be deselected
   */
  deselectAll(checkboxes: NodeListOf<HTMLInputElement>): void{
    this.selectedNeeds = [];    
    checkboxes.forEach(checkbox => checkbox.checked = false)
  }

  /**
   * Adds the given need to the list of selected needs once selected
   * @param need The selected need
   */
  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  /**
   * Disables the given HTML input element if the funding basket is empty
   * @param element The id of the element to disable
   * @returns True if the funding basket is empty, False otherwise 
   */
  isEmpty(element: string): boolean{
    const isEmpty = this.fundingBasket.length === 0;
    (document.getElementById(element) as HTMLInputElement).disabled = isEmpty;
    return isEmpty;
  }

  /**
   * Deletes a given need from the funding basket
   * @param need The need to be removed
   */
  removeNeedFromBasket(need: Need): void{
    this.fundingBasket = this.fundingBasket.filter(n => n !== need);
    this.helperService.removeNeedFromBasket(need, this.username).subscribe();
  }
  
  /**
   * Checks out the entire funding basket
   */
  checkoutBasket(): void{
    this.helperService.checkoutBasket(this.username).subscribe();
    this.fundingBasket = []
  }

  /**
   * Removes all selected needs from the basket
   */
  removeMultipleFromBasket(): void{
    this.selectedNeeds.forEach(need => this.removeNeedFromBasket(need));
  }
}
