import { Component, Input, OnInit } from '@angular/core';
import { MessageBoardService } from '../message-board.service';

/**
 * Message Board Component that handles functionality for managing the message board
 */
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

  /**
   * Loads the list of messages with messages on initialization
   */
  ngOnInit(): void {
    this.loadMessages();
  }

  /**
   * Retrieves the message board
   */
  loadMessages(): void {
    this.messageBoardService.getMessageBoard().subscribe(messages => this.messages = messages);
  }

  /**
   * Adds a user's message to the message board
   * @param message The message to be added
   * @param username The username of the user
   */
  addMessage(message: String, username: String): void {
    this.messageBoardService.addMessage(message, username).subscribe(message => {this.messages.push(message);});
  }

  /**
   * Deletes a message from the message board
   * @param message The message to be deleted
   */
  deleteMessage(message: String): void {
    this.messageBoardService.deleteMessage(message).subscribe(() => {
      this.messages = this.messages.filter(m => m !== message);
    });
  }
}
