package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Need;

/**
 * The interface for the Helper object persistence
 */
public interface HelperDAO {
    /**
     * Removes need from the basket
     * 
     * @param need The need to remove from the basket
     * @param username The username of the helper whose basket the need must be removed from
     * 
     * @return True if the need was removed, False otherwise
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public boolean removeNeedFromBasket(Need need, String username) throws IOException;

    /**
     * Gets need from the funding basket
     * 
     * @param need The need to add to the basket
     * @param username The username of the helper seeking to add the need
     * 
     * @return True if the need was added, False otherwise
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public boolean addNeedToBasket(Need need, String username) throws IOException;
    
    /**
     * Gets entire funding basket
     * 
     * @param username The username of the helper
     * 
     * @return List of need objects, possibly empty
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public List<Need> getBasket(String username) throws IOException;

    /**
     * Adds message to the message board
     * 
     * @param message The message to add to the message board
     * @param username The username of the user adding the message
     * 
     * @return True if the message was added, False otherwise
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public boolean addMessage(String message, String username) throws IOException; 
}

