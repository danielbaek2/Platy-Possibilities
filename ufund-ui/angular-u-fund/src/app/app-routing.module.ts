import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { NeedComponent } from './need/need.component';
import { HelperComponent } from './helper/helper.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { LoginComponent } from './login/login.component';

const routes: Routes = [
  { path: 'needs', component: NeedComponent },
  { path: 'helper', component: HelperComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: 'detail/:id', component: NeedDetailComponent },
  { path: 'login', component: LoginComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }