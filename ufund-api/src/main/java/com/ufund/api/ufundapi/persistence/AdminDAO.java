package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

/**
 * The interface for the Admin object persistence
 */
public interface AdminDAO {
    /**
     * Gets entire messageBoard
     * 
     * @return List of string objects, possibly empty
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public List<String> getMessageBoard() throws IOException;

    /**
     * Adds a message to the data storage
     * 
     * @param message Message to add
     * 
     * @return True if the message was added, False otherwise
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public boolean getMessage(String message) throws IOException;

    /**
     * Deletes a message that is inside data storage
     * 
     * @param message Message to delete
     * 
     * @return True if the message was deleted, False otherwise
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public boolean deleteMessage(String message) throws IOException;
}
