import { Component, Input } from '@angular/core';
import { Need } from '../need';
import { FormsModule } from '@angular/forms';
import { NgIf, UpperCasePipe } from '@angular/common';

@Component({
  standalone: true,
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrl: './need-detail.component.css',
  imports: [FormsModule, NgIf, UpperCasePipe],
})
export class NeedDetailComponent {
  @Input() need?: Need;
}
