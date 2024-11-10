package com.ufund.api.ufundapi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Helper extends User {

    private List<Need> fundingBasket;

    @JsonCreator
    public Helper(String username){
        super(username);
        this.fundingBasket = new ArrayList<>();
    }
  
    /***
     * Remove a need from the funding basket
     * @param index The id of the need to be removed from the basket
     */
    public void removeNeedFromBasket(Need need){
        boolean removed = fundingBasket.remove(need);
        System.out.println(removed);
    }

    public void addNeedToBasket(Need need){
        fundingBasket.add(need);
    }

    public List<Need> getBasket(){
        return fundingBasket;
    }

    public void addMessage(MessageBoard messageBoard, String message) throws IOException {
        messageBoard.addMessage(message);
    }
}
