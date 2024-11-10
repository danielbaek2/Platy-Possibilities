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
import { MessageService } from '../message.service';
import { RouterModule } from '@angular/router';

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

  constructor(private needService: NeedService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.getNeeds();
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }

  add(title: string): void {
    title = title.trim();
    if (!title) { return; }
    this.needService.addNeed({ title } as Need).subscribe({next: (need) => {this.needs.push(need);}, error: (error) => {this.messageService.add(error.message);}});
  }

  delete(need: Need): void {
    this.needs = this.needs.filter(n => n !== need);
    this.needService.deleteNeed(need.id).subscribe();
  }

  selectMultiple(need: Need): void{
    this.selectedNeeds.push(need);
  }

  deleteMultiple(): void{
    this.selectedNeeds.forEach(need => this.delete(need));
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
    this.selectedNeeds = this.needs;
    checkboxes.forEach(checkbox => checkbox.checked = true)
  }

  deselectAll(checkboxes: NodeListOf<HTMLInputElement>): void{
    this.selectedNeeds = [];    
    checkboxes.forEach(checkbox => checkbox.checked = false)
  }
}