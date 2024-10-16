package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HelperFileDAO implements HelperDAO{

    private String filename;

    private ObjectMapper objectMapper = null;

    private HashMap<String, User> users;

    private static int nextID;
    /**
     * CupboardFileDao -  Current instance of the cupboard data access.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    // not sure how to recieve file name.

    public HelperFileDAO(@Value("data/users.json") String filename, ObjectMapper objectmapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.users = new HashMap<>();
        loadFile();
    }

    /**
     * loadFile - Loads the needs into a hashmap, where the key is the ID, the value is the full need structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        User[] userList = objectMapper.readValue(new File(filename), User[].class);
        for (User currUser : userList){ // for each need, load hashmap of needs with ID and need structure.
            users.put(currUser.getUsername(), currUser);
        }
    }

    /**
     * saveFile - Saves the hashmap
     * @throws IOException
     */
    private void saveFile() throws IOException{
        User[] users = this.users.values().toArray(new User[0]);
        objectMapper.writeValue(new File(this.filename),users);
    }

    public void removeNeedFromBasket(Need need) {
        synchronized(users) {
            
        }
    }

    public void addNeedToBasket(Need need) {
        synchronized(users) {
            
        }
    }

    public List<Need> getBasket() {
        synchronized(users) {
            return getBasket();
        }
    }
}

