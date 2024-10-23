import { Injectable } from '@angular/core';
import { InMemoryDbService } from 'angular-in-memory-web-api';
import { Need } from './need';
import { User } from './user';

@Injectable({
  providedIn: 'root',
})
export class InMemoryDataService implements InMemoryDbService {
  createDb() {
    const needs = [
      {id: 11, title: "Defend Pesto", description: "Defend the baby penguin Pesto from pestering tourists.", total_funding: 100},
      {id: 12, title: "Protect Turtle Nests", description: "Help protect endangered sea turtle nests from poachers and predators.", total_funding: 150},
      {id: 13, title: "Save the Red Fox", description: "Assist in the preservation of the red fox habitat from urban expansion.", total_funding: 200},
      {id: 14, title: "Rescue Injured Eagles", description: "Support the rescue and rehabilitation of injured bald eagles.", total_funding: 300},
      {id: 15, title: "Monitor Dolphin Migration", description: "Fund research to track and monitor dolphin migration patterns.", total_funding: 250},
      {id: 16, title: "Conserve Coral Reefs", description: "Provide resources to protect and restore coral reef ecosystems.", total_funding: 400},
      {id: 17, title: "Guard Bear Dens", description: "Ensure the safety of bear dens from illegal hunting and deforestation.", total_funding: 350},
      {id: 18, title: "Rehabilitate Orphaned Kangaroos", description: "Help rehabilitate and release orphaned kangaroos back into the wild.", total_funding: 275},
      {id: 19, title: "Restore Butterfly Habitats", description: "Aid in the restoration of natural habitats for endangered butterfly species.", total_funding: 180},
      {id: 20, title: "Protect Snow Leopards", description: "Contribute to the conservation of snow leopards facing habitat loss.", total_funding: 500},
      {id: 21, title: "Safeguard Elephant Corridors", description: "Support the protection of elephant migration corridors from human interference.", total_funding: 450},
      {id: 22, title: "Protect Moo Deng", description: "Moo Deng, the rightful heir of Thailand, must be protected from blood-thirsty predators and disrespectful tourists.", total_funding: 150}
    ];

    const helper = {fundingBasket: [], user: new User('JaneDoe', false)}
    return {needs, helper};
  }

  // Overrides the genId method to ensure that a need always has an id.
  // If the needs array is empty,
  // the method below returns the initial number (11).
  // if the needs array is not empty, the method below returns the highest
  // hero id + 1.
  genId(needs: Need[]): number {
    return needs.length > 0 ? Math.max(...needs.map(need => need.id)) + 1 : 11;
  }
}