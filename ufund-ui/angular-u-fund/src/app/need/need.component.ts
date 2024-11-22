import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import {
  NgFor,
  NgIf,
  UpperCasePipe,  
} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NeedDetailComponent } from "../need-detail/need-detail.component";
import { NeedService } from '../need.service';
import { RouterModule } from '@angular/router';

/**
 * Need Component that handles functionality for managing the needs cupboard
 */
@Component({
  selector: 'app-need',
  templateUrl: './need.component.html',
  styleUrl: './need.component.css',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgFor,
    UpperCasePipe,
    NeedDetailComponent,
    RouterModule,
],
})
export class NeedComponent implements OnInit {
  needs: Need[] = [];
  buttonText = 'Select all';
  private clicked = true;
  private selectedNeeds: Need[] = [];

  constructor(private needService: NeedService) {}

  /**
   * Loads the needs cupboard with needs on initialization
   */
  ngOnInit(): void {
    this.getNeeds();
  }

  /**
   * Retrieves the list of needs from the need service
   */
  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  /**
   * Creates and adds a new need to the list of needs in the cupboard
   * Exits early without creating the new need if any field is missing
   * @param title Title of the new need
   * @param description Description of the new need
   * @param cost Cost of funding each unit of the new need
   * @param quantity Amount of units that can be funded for the new need
   */
  add(title: string, description: string, cost: number, quantity: number): void {
    title = title.trim(); description = description.trim();
    if (!title || !description || !cost || !quantity) { return; }
    const newNeed: Need = {
      id: 0,
      title,
      description,
      cost,
      quantity,
      quantity_funded: 0
    };
    this.needService.addNeed(newNeed).subscribe({next: (need) => {this.needs.push(need)}});
  }

  /**
   * Deletes a given need from the cupboard
   * @param need The need to be deleted
   */
  delete(need: Need): void {
    this.needs = this.needs.filter(n => n !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

  /**
   * Adds the given need to the list of selected needs once selected
   * @param need The selected need
   */
  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  /**
   * Deletes all selected needs from the cupboard
   */
  deleteMultiple(): void{
    this.selectedNeeds.forEach(need => this.delete(need));
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
    this.selectedNeeds = this.needs;
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
}