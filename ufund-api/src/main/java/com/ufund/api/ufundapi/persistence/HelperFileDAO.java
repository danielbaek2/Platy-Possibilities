package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Helper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class HelperFileDAO implements HelperDAO{

    private String filename;

    private ObjectMapper objectMapper = null;

    private HashMap<String, Helper> helpers;

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

    public void removeNeedFromBasket(Need need,String username) throws IOException {
        synchronized(helpers) {
            Helper helper = helpers.get(username);
            if (helper != null){
                List<Need> basket = helper.getBasket();
                if (basket.contains(need)){
                    helper.removeNeedFromBasket(need);
                    saveFile();
                }
            }
        }
    }

    public void addNeedToBasket(Need need, String username) throws IOException {
        synchronized (helpers) {
            Helper helper = helpers.get(username);
            if (helper != null) {
                List<Need> basket = helper.getBasket();
                if (basket.contains(need)) {
                } else {
                    helper.addNeedToBasket(need);
                }
            }
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
}

