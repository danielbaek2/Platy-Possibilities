import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MessagesComponent } from './messages/messages.component';
import { AppComponent } from './app.component';
import { NeedComponent } from './need/need.component';
import { NeedDetailComponent } from './need-detail/need-detail.component';
import { HelperComponent } from './helper/helper.component';
import { HttpClientModule } from '@angular/common/http';
import { HttpClientInMemoryWebApiModule } from 'angular-in-memory-web-api';
import { InMemoryDataService } from './in-memory-data.service';
import { NeedSearchComponent } from './need-search/need-search.component';

@NgModule({
  declarations: [
    AppComponent,
    MessagesComponent,
    HelperComponent,
    NeedSearchComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    NeedComponent,
    HttpClientModule,
    HttpClientInMemoryWebApiModule.forRoot(InMemoryDataService, { dataEncapsulation: false })
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
