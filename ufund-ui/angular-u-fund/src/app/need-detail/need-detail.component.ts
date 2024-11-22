import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { FormsModule } from '@angular/forms';
import { NgIf, UpperCasePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { NeedService } from '../need.service';
import { CurrentUserService} from "../current-user.service";

/**
 * Need Detail Component for displaying and saving the details of a need
 */
@Component({
  standalone: true,
  selector: 'app-need-detail',
  templateUrl: './need-detail.component.html',
  styleUrl: './need-detail.component.css',
  imports: [FormsModule, NgIf, UpperCasePipe],
})
export class NeedDetailComponent implements OnInit {
  need: Need | undefined;
  isAdmin: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private needService: NeedService,
    private location: Location,
    private currentUserService: CurrentUserService
  ) {}

  /**
   * Loads the details of the need and stores whether or not the current user is an admin
   */
  ngOnInit(): void {
    this.getNeed();
    this.isAdmin = this.currentUserService.isAdmin();
  }

  /**
   * Gets a need based on the id in the url
   */
  getNeed(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }

  /**
   * Routes back to the previous page
   */
  goBack(): void {
    this.location.back();
  }

  /**
   * Saves the updated fields of the need
   */
  save(): void {
    if (this.need) {
      this.needService.updateNeed(this.need)
        .subscribe(() => this.goBack());
    }
  }
}
