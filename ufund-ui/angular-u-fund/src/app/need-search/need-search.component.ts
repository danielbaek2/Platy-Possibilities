import {Component, inject, OnInit} from '@angular/core';

import { Observable, Subject } from 'rxjs';

import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

import { Need } from '../need';
import { NeedService } from '../need.service';
import {User} from "../user";
import {HELPER} from "../mock-helper";
import {CurrentUserService} from "../current-user.service";
import {HelperService} from "../helper.service";

/**
 * Need Search Component that handles functionality for searching for a need
 */
@Component({
  selector: 'app-need-search',
  templateUrl: './need-search.component.html',
  styleUrls: [ './need-search.component.css' ]
})
export class NeedSearchComponent implements OnInit {
  needs: Need[] = [];
  fundingBasket: Need[] = [];
  needs$!: Observable<Need[]>;
  private searchTerms = new Subject<string>();
  user: User = HELPER.user; // temporary hardcoded value
  currentUserService = inject(CurrentUserService);

  constructor(private needService: NeedService,
  private helperService: HelperService) {}

  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);

  }

  ngOnInit(): void {
    this.needs$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.needService.searchNeeds(term)),
    );
    this.currentUserService.currentUser$.subscribe((user) => {
      if (user) {
        this.user = user;
        this.getBasket();
      }
    });
  }

  /**
   * Adds a given need from the search to the funding basket
   * @param need The need to be added
   */
  addNeedToBasket(need: Need): void{
    this.helperService.addNeedToBasket(need, this.user.username).subscribe(need => {this.fundingBasket.push(need);});
  }

  /**
   * Retrieves the funding basket from the helper service
   * Updates the funding basket property with the retrieved data
   */
  getBasket(): void{
    this.helperService.getBasket(this.user.username).subscribe(fundingBasket => this.fundingBasket = fundingBasket);
  }
}
