import { Component } from '@angular/core';
import { Need } from '../need';
import { NEEDS } from '../mock-needs';
import {
  NgFor,
  NgIf,
  UpperCasePipe,
} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NeedDetailComponent } from "../need-detail/need-detail.component";
import { NeedService } from '../need.service';
import { MessageService } from '../message.service';

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
    NeedDetailComponent
],
})
export class NeedComponent {
  needs: Need[] = [];
  selectedNeed?: Need;

  constructor(private needService: NeedService, private messageService: MessageService) {}

  ngOnInit(): void {
    this.getNeeds();
  }

  onSelect(need: Need): void {
    this.selectedNeed = need;
    this.messageService.add('NeedsComponent: Selected need id=${need.id}');
  }

  getNeeds(): void {
    this.needService.getNeeds().subscribe(needs => this.needs = needs);
  }
}