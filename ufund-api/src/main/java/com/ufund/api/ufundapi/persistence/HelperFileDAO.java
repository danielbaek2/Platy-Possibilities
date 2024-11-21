package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User.Helper;
import com.ufund.api.ufundapi.model.MessageBoard;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based persistence for Helpers
 */
@Component("helperFileDAO")
public class HelperFileDAO extends UserFileDAO implements HelperDAO {

    private MessageBoard messageBoard;
    
    /**
     * HelperFileDao -  Current instance of the helper data access object.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    public HelperFileDAO(@Value("data/users.json") String filename, ObjectMapper objectmapper, MessageBoard messageBoard) throws IOException{
        super(filename, objectmapper);
        this.messageBoard = messageBoard;
    }

    /**
     * Removes the need if the helper exists and if the need is in the funding basket.
     * @param need - The need to remove from the basket
     * @param username - The username of the helper
     * @return - True if helper exists and need is removed from the basket, false otherwise.
     * @throws IOException if there is an issue with the data storage
     */
    public boolean removeNeedFromBasket(Need need,String username) throws IOException {
        synchronized(users) {
            Helper helper = (Helper) users.get(username);
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

    /**
     * Adds the need if the helper exists and if the need is not in the funding basket.
     * @param need - The need to add from the basket
     * @param username - The username of the helper
     * @return - True if helper exists and the need is added to the basket, false otherwise.
     * @throws IOException if there is an issue with the data storage
     */
    public boolean addNeedToBasket(Need need, String username) throws IOException {
        synchronized (users) {
            Helper helper = (Helper) users.get(username);
            if (helper != null) {
                List<Need> basket = helper.getBasket();
                if (basket.contains(need)) {
                    return false;
                } else {
                    if (need.fundable()){
                        helper.addNeedToBasket(need);
                        saveFile();
                        return true;
                    }
                    return false;
                }
            }
            return false;
        }
    }

    /**
     * Gets the funding basket if the helper exists.
     * @param username - The username of the helper
     * @return - The funding basket (list of needs), could be null.
     * @throws IOException if there is an issue with the data storage
     */
    public List<Need> getBasket(String username) throws IOException {
        synchronized(users) {
            Helper helper = (Helper) users.get(username);
            if (helper != null){
                return helper.getBasket();
            }
            else{
                return null;
            }
        }
    }

    /**
     * Adds a message to the message board if the helper exists and the message is not on the message board.
     * @param message - The message to add to the board
     * @param username - The username of the helper
     * @return - True if the helper exists and the message was added, False otherwise.
     * @throws IOException if there is an issue with the data storage
     */
    public boolean addMessage(String message, String username) throws IOException {
        synchronized(users) {
            Helper helper = (Helper) users.get(username);
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