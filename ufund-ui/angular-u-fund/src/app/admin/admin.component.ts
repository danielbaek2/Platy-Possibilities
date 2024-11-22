import { AdminService } from '../admin.service';
import { Component, OnInit } from '@angular/core';
import { MessageBoardService } from '../message-board.service';

/**
 * Admin Component that provides functionality for managing the message board
 */
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.css'
})
export class AdminComponent implements OnInit {
  messages: String[] = [];

  constructor(private adminService: AdminService, private messageBoardService: MessageBoardService) {}

  /**
   * Loads the message board with messages on initialization
   */
  ngOnInit(): void {
    this.getMessageBoard();
  }

  /**
   * Retrieves the message board data from the service
   * Updates the messages property with the retrieved data
   */
  getMessageBoard(): void {
    this.messageBoardService.getMessageBoard().subscribe(messages => this.messages = messages);
  }

  /**
   * Deletes a given message from the message board and updates the local messages property
   * @param message The message to be deleted
   */
  delete(message: String): void {
    this.messages = this.messages.filter(m => m !== message);
    this.messageBoardService.deleteMessage(message).subscribe();
  }
}
