package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Class representing an Admin User
 */
public class Admin extends User {

    private List<String> messageBoard;

    /**
     * Constructor for creating an admin with a given username and message board
     * 
     * @param username the username of the admin
     * @param messageBoard the message board (list of strings) where helper suggestions are stored
     * 
     */
    @JsonCreator
    public Admin(String username) {
        super(username);
        messageBoard = new ArrayList<>();
    }

    /**
     * Get the entire message board
     * @return the message board
     */
    public List<String> getMessageBoard() {
        return messageBoard;
    }

    /**
     * Add the string message to the message board
     * @param message the message to add
     */
    public void getMessage(String message) {
        messageBoard.add(message);
    }

    /**
     * Delete the given message fromS the message board
     * @param message the message to delete
     */
    public void deleteMessage(String message) {
        messageBoard.remove(message);
    }
}
