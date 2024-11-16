package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based persistence for Users
 */
@Component
public class UserFileDAO implements UserDAO{

    private String filename;

    private ObjectMapper objectMapper = null;

    private HashMap<String, Helper> helpers;
    
    /**
     * UserFileDAO -  Current instance of the user data access object.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    public UserFileDAO(@Value("ufund-api/data/users.json") String filename, ObjectMapper objectmapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.helpers = new HashMap<>();
        loadFile();
    }

    /**
     * Loads the needs into a hashmap, where the key is the username, the value is the full helper structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        Helper[] helperList = objectMapper.readValue(new File(filename), Helper[].class);
        for (Helper currHelper : helperList){
            helpers.put(currHelper.getUsername(), currHelper);
        }
    }

    /**
     * saveFile - Saves the hashmap
     * @throws IOException
     */
    private void saveFile() throws IOException{
        Helper[] helpers = this.helpers.values().toArray(new Helper[0]);
        objectMapper.writeValue(new File(this.filename),helpers);
    }
    
    /**
     * User Login Functionality
     */
    @Override
    public boolean verifyUser(String username) {
        synchronized(helpers){
            if (helpers.get(username) != null){
                return true;
            }
            return false;
        }
    }

    /**
     * Checks if the user is admin
     * 
     * @param username - The username to check
     * @return - True if the user is an admin, false otherwise
     */
    @Override
    public boolean isAdmin(String username) {
        synchronized(helpers){
            return !helpers.containsKey(username);
        }
    }

    /**
     * Searches a list of helpers for helpers that match the given username.
     * 
     * @param username - The username to search for
     * @return - The list of helpers with the given username, could be null.
     * @throws IOException if there is an issue with the data storage
     */
    public List<Helper> userSearch(String username) throws IOException {
        List<Helper> matchingUsers = new ArrayList<>();

        synchronized (helpers) {
            for (Helper helper : helpers.values()) {
                if (helper.getUsername().toLowerCase().contains(username.toLowerCase())) {
                    matchingUsers.add(helper);
                }
            }
        }
        return matchingUsers;
    }
}