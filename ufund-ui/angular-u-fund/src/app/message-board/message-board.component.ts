import { Component, Input, OnInit } from '@angular/core';
import { MessageBoardService } from '../message-board.service';

@Component({
  selector: 'app-message-board',
  templateUrl: './message-board.component.html',
  styleUrl: './message-board.component.css'
})
export class MessageBoardComponent implements OnInit {
  @Input() canViewMessages: boolean = false;
  @Input() canDeleteMessages: boolean = false;
  @Input() canAddMessages: boolean = false;
  messages: String[] = [];
  message: String = '';

  constructor(private messageBoardService: MessageBoardService) { }

  ngOnInit(): void {
    this.loadMessages();
  }

  loadMessages(): void {
    this.messageBoardService.getMessageBoard().subscribe(messages => this.messages = messages);
  }

  addMessage(message: String, username: String): void {
    this.messageBoardService.addMessage(message, username).subscribe(message => {this.messages.push(message);});
  }

  deleteMessage(message: String): void {
    this.messageBoardService.deleteMessage(message).subscribe(() => {
      this.messages = this.messages.filter(m => m !== message);
    });
  }
}
