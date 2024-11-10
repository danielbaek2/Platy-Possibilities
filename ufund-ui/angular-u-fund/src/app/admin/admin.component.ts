import { AdminService } from '../admin.service';
import { Component, OnInit } from '@angular/core';
import {
  NgFor,
  NgIf,
  UpperCasePipe,  
} from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MessageService } from '../message.service';
import { RouterModule } from '@angular/router';
import { MessageBoardService } from '../message-board.service';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
  messages: String[] = [];

  constructor(private adminService: AdminService, private messageBoardService: MessageBoardService) {}

  ngOnInit(): void {
    this.getMessageBoard();
  }

  getMessageBoard(): void {
    this.messageBoardService.getMessageBoard().subscribe(messages => this.messages = messages);
  }

  delete(message: String): void {
    this.messages = this.messages.filter(m => m !== message);
    this.messageBoardService.deleteMessage(message).subscribe();
  }
}
