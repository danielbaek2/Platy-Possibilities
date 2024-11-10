package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.MessageBoard;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based persistence for Helpers
 */
@Component
public class HelperFileDAO implements HelperDAO {

    private String filename;
    private ObjectMapper objectMapper = null;
    private HashMap<String, Helper> helpers;
    private MessageBoard messageBoard;
    
    /**
     * HelperFileDao -  Current instance of the helper data access object.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    public HelperFileDAO(@Value("ufund-api/data/helpers.json") String filename, ObjectMapper objectmapper, MessageBoard messageBoard) throws IOException{
        this.filename = filename;
        this.objectMapper = objectmapper;
        this.helpers = new HashMap<>();
        this.messageBoard = messageBoard;
        loadFile();
    }

    /**
     * loadFile - Loads the needs into a hashmap, where the key is the ID, the value is the full need structure.
     * @throws IOException
     */
    private void loadFile() throws IOException{
        Helper[] helperList = objectMapper.readValue(new File(filename), Helper[].class);
        for (Helper currHelper : helperList){ // for each helper, load hashmap of helpers with username and helper structure.
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

    public boolean addMessage(String message, String username) throws IOException {
        synchronized(helpers) {
            Helper helper = helpers.get(username);
            if (helper != null) {
                if (messageBoard.getMessageBoard().contains(message)) {
                    return false;
                } else {
                    helper.addMessage(messageBoard, message);
                    saveFile();
                    return true;
                }
            }
            return false;
        }
    }
}