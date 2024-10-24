import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MessagesComponent } from './messages/messages.component';
import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HelperComponent } from './helper/helper.component';
import { HttpClientModule } from '@angular/common/http';
import { NeedSearchComponent } from './need-search/need-search.component';
import { AdminComponent } from './admin/admin.component';
import { LoginComponent } from "./login/login.component";

@NgModule({
  declarations: [
    AppComponent,
    MessagesComponent,
    HelperComponent,
    NeedSearchComponent,
    AdminComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NeedComponent,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
