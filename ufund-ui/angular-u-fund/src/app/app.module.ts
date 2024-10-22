import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MessagesComponent } from './messages/messages.component';
import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    MessagesComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NeedComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
