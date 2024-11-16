package com.ufund.api.ufundapi.model;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ufund.api.ufundapi.persistence.AdminDAO;

/**
 * Class representing a MessageBoard 
 */
@Component
public class MessageBoard {
    private final AdminDAO adminDAO;

    /**
     * Constructor for creating a message board with the AdminDAO object
     * @param adminDAO
     */
    @JsonCreator
    public MessageBoard(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    /**
     * Retrieves the entire message baord
     * 
     * @return the message board (list of strings)
     * @throws IOException
     */
    public List<String> getMessageBoard() throws IOException {
        return adminDAO.getMessageBoard();
    }

    /**
     * Adds a message to the message board
     * 
     * @param message the message to be added
     * @return boolean value of whether the message was added (True or False)
     * @throws IOException
     */
    public boolean addMessage(String message) throws IOException {
        return adminDAO.getMessage(message);
    }
    
    /**
     * Removes a message from the message board
     * 
     * @param message the message to be removed
     * @return boolean value of whether the message was deleted (True or False)
     * @throws IOException
     */
    public boolean deleteMessage(String message) throws IOException {
        return adminDAO.deleteMessage(message);
    }
}
