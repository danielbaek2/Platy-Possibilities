package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Helper;

/**
 * The interface for the User object persistence
 */
public interface UserDAO {

    /**
     * Checks if a user is valid based on the username
     * 
     * @param username The username of the user to verify
     * 
     * @return True if the user exists, False otherwise
     */
    public boolean verifyUser(String username);

    /**
     * Determines if the user is Admin or not (based on username)
     * 
     * @param username The username of the user to check
     * 
     * @return True if the user is Admin, False otherwise
     */
    public boolean isAdmin(String username);

    /**
     * Retrieves all users that match the username inside data storage
     * 
     * @param username The username to search for
     * 
     * @return List of Helpers that match the username
     * 
     * @throws IOException if there is an issue with the data storage
     */
    public List<Helper> userSearch(String username) throws IOException;
}

