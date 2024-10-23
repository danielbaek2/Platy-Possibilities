import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NeedComponent } from './need/need.component';
import { HelperComponent } from './helper/helper.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';

const routes: Routes = [
  { path: 'needs', component: NeedComponent },
  { path: 'helper', component: HelperComponent },
  { path: 'helper?username=', component: HelperComponent },
  { path: '', redirectTo: '/helper', pathMatch: 'full' },
  { path: 'detail/:id', component: NeedDetailComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }