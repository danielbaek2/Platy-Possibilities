package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserFileDAO implements UserDAO{

    private String filename;

    private ObjectMapper objectMapper = null;

    private HashMap<String, Helper> helpers;
    
    /**
     * CupboardFileDao -  Current instance of the cupboard data access.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    // not sure how to recieve file name.
    // changed file to helpers.json for debugging purposes
    public UserFileDAO(@Value("data/helpers.json") String filename, ObjectMapper objectmapper) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.helpers = new HashMap<>();
        loadFile();
    }

    /**
     * loadFile - Loads the needs into a hashmap, where the key is the ID, the value is the full need structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        Helper[] helperList = objectMapper.readValue(new File(filename), Helper[].class);
        for (Helper currHelper : helperList){ // for each need, load hashmap of needs with ID and need structure.
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

    public boolean removeNeedFromBasket(Need need,String username) throws IOException {
        synchronized(helpers) {
            Helper helper = helpers.get(username);
            if (helper != null){
                List<Need> basket = helper.getBasket();
                if (basket.contains(need)) {
                    helper.removeNeedFromBasket(need);
                    saveFile();
                    return true;
                }else{
                    return false;
                }
            }
            return false;
        }
    }

    public boolean addNeedToBasket(Need need, String username) throws IOException {
        synchronized (helpers) {
            Helper helper = helpers.get(username);
            if (helper != null) {
                List<Need> basket = helper.getBasket();
                if (basket.contains(need)) {
                    return false;
                } else {
                    helper.addNeedToBasket(need);
                    saveFile();
                    return true;
                }
            }
            return false;
        }
    }

    public List<Need> getBasket(String username) throws IOException {
        synchronized(helpers) {
            Helper helper = helpers.get(username);
            if (helper != null){
                return helper.getBasket();
            }
            else{
                return null;
            }
        }
    }

    
    // User Login Functionality
    @Override
    public boolean verifyUser(String username) {
        synchronized(helpers){
            if (helpers.get(username) != null){
                return true;
            }
            return false;
        }
    }

    @Override
    public boolean isAdmin(String username) {
        synchronized(helpers){
            return !helpers.containsKey(username);
        }
    }
}

