package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import com.ufund.api.ufundapi.model.Need;


/**
 * The interface for the Need object persistence
 */
public interface CupboardDAO {
    /**
     * Gets all {@linkplain Need needs}
     * 
     * @return Array of {@link Need need} objects, possibly empty
     * 
     * @throws IOException if there is an issue with the data storage
     */
    Need[] getNeeds() throws IOException;

    /**
     * Gets all {@linkplain Need needs} whose name contains substring
     * 
     * @param substring The string we are using to search and match
     * 
     * @return Array of {@link Need need} objects whose name contains the substring, possibly empty
     * 
     * @throws IOException if there is an issue with the data storage
     */
    Need[] searchNeeds(String substring) throws IOException;

    /**
     * Get a {@linkplain Need need} with the given id
     * 
     * @param id the id of the object we are getting
     * 
     * @return {@link Need need} object whose id matches given int
     * 
     * @return NULL if no {@link Need need} object with given id is found
     * 
     * @throws IOException if there is an issue with the data storage
     */
    Need getNeed(int id) throws IOException;


    /**
     * Create and save a {@linkplain Need need} to data storage
     * 
     * @param need {@linkplain Need need} object we are creating and saving to storage
     * 
     * @return new {@link Need need} object if created
     * 
     * @return false if object creation failed
     * 
     * @throws IOException if there is an issue with the data storage
     */
    Need createNeed(Need need) throws IOException;


    /**
     * Update a {@linkplain Need need} that is inside data storage
     * 
     * @param need {@link Need need} object to be updated
     * 
     * @return the updated {@link Need need}
     * 
     * @return NULL if need object couldn't be found
     * 
     * @throws IOException if there is an issue with the data storage
     */
    Need updateNeed(Need need) throws IOException;

    /**
     * Delete a {@linkplain Need need} from data storage
     * 
     * @param id the id of the {@link Need need} object to be deleted
     * 
     * @return True if {@link Need need} was successfully deleted, False if {@link Need need} was not found
     * 
     * @throws IOException if there is an issue with the data storage
     */
    boolean deleteNeed(int id) throws IOException;
}
