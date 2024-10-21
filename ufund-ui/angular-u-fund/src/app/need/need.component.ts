import { Component } from '@angular/core';
import { Need } from '../need';

@Component({
  selector: 'app-need',
  templateUrl: './need.component.html',
  styleUrl: './need.component.css'
})
export class NeedComponent {
  need: Need = {
    id: 1,
    title: "Protecting Moo Deng",
    description: "Moo Deng, the rightful heir of Thailand, must be protected from blood-thirsty predators and disrespectful tourists.",
    total_funding: 100
  };
}