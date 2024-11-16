package com.ufund.api.ufundapi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Class representing a Helper user
 */
public class Helper extends User {

    private List<Need> fundingBasket;

    /**
     * Constructor for creating a helper with a given username and funding basket
     * 
     * @param username the username of the helper
     */
    @JsonCreator
    public Helper(String username){
        super(username);
        this.fundingBasket = new ArrayList<>();
    }
  
    /***
     * Remove a need from the funding basket.
     * 
     * @param need The need to be removed from the basket
     */
    public void removeNeedFromBasket(Need need){
        fundingBasket.remove(need);
    }

    /**
     * Add a need to the funding basket.
     * 
     * @param need The need to be added to the basket
     */
    public void addNeedToBasket(Need need){
        fundingBasket.add(need);
    }

    /**
     * Retrieves the entire funding basket
     * 
     * @return The funding basket of the helper
     */
    public List<Need> getBasket(){
        return fundingBasket;
    }

    /**
     * Adds a message to the message board
     * 
     * @param messageBoard The board to add a message to
     * @param message The message to add to the board
     * @throws IOException
     */
    public void addMessage(MessageBoard messageBoard, String message) throws IOException {
        messageBoard.addMessage(message);
    }
}
