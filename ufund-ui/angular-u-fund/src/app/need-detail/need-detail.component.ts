import { Component, OnInit } from '@angular/core';
import { Need } from '../need';
import { FormsModule } from '@angular/forms';
import { NgIf, UpperCasePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { NeedService } from '../need.service';
import { CurrentUserService} from "../current-user.service";

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

  ngOnInit(): void {
    this.getNeed();
    this.isAdmin = this.currentUserService.isAdmin();
  }

  getNeed(): void {
    const id = parseInt(this.route.snapshot.paramMap.get('id')!, 10);
    this.needService.getNeed(id)
      .subscribe(need => this.need = need);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    if (this.need) {
      this.needService.checkNeedIdExists(this.need.id).subscribe((exists) => {
        if (exists) {
          alert(`A need with ID ${this.need?.id} already exists. Please choose a different ID.`);
        } else {
          this.needService.updateNeed(this.need!).subscribe(() => this.goBack());
        }
      });
    }
  }

}
