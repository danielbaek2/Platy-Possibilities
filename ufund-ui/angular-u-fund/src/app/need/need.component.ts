import { Component } from '@angular/core';
import { Need } from '../need';
import { NEEDS } from '../mock-needs';
import {
  NgFor,
  NgIf,
  UpperCasePipe,
} from '@angular/common';
import { FormsModule } from '@angular/forms';

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
  ],
})
export class NeedComponent {
  needs = NEEDS;
  selectedNeed?: Need;

  onSelect(need: Need): void {
    this.selectedNeed = need;
  }
}