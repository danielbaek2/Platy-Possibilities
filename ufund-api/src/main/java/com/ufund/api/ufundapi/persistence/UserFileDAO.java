package com.ufund.api.ufundapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.UserExperiement;

@Component
public abstract class UserFileDAO implements UserDAO{
    private String filename;
    private ObjectMapper objectMapper = null;
    protected HashMap<String, UserExperiement> users;
    
    /**
     * UserFileDAO -  Current instance of the user data access object.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    public UserFileDAO(@Value("data/usersexperiment.json") String filename, ObjectMapper objectmapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.users = new HashMap<>();
        loadFile();
    }

    /**
     * Loads the needs into a hashmap, where the key is the username, the value is the full helper structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        UserExperiement[] userList = objectMapper.readValue(new File(filename), UserExperiement[].class);
        for (UserExperiement currUser : userList){
            users.put(currUser.getUsername(), currUser);
        }
    }

    /**
     * saveFile - Saves the hashmap
     * @throws IOException
     */
    protected void saveFile() throws IOException{
        UserExperiement[] users = this.users.values().toArray(new UserExperiement[0]);
        objectMapper.writeValue(new File(this.filename),users);
    }
    
    /**
     * User Login Functionality
     */
    @Override
    public boolean verifyUser(String username) {
        synchronized(users){
            if (users.get(username) != null){
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
        synchronized(users){
            return username.equals("Admin");
        }
    }

    /**
     * Searches a list of users for users that match the given username.
     * 
     * @param username - The username to search for
     * @return - The list of users with the given username, could be null.
     * @throws IOException if there is an issue with the data storage
     */
    public List<UserExperiement> userSearch(String username) throws IOException {
        List<UserExperiement> matchingUsers = new ArrayList<>();

        synchronized (users) {
            for (UserExperiement user : users.values()) {
                if (user.getUsername().toLowerCase().contains(username.toLowerCase())) {
                    matchingUsers.add(user);
                }
            }
        }
        return matchingUsers;
    }

}
