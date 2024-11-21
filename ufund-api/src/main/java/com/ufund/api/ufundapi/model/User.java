package com.ufund.api.ufundapi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type", visible = true)
@JsonSubTypes({
    @JsonSubTypes.Type(value = com.ufund.api.ufundapi.model.User.Helper.class, name = "helper"),
    @JsonSubTypes.Type(value = com.ufund.api.ufundapi.model.User.Admin.class, name = "admin")
})
public abstract class User {
    @JsonProperty("type") private String type;
    @JsonProperty("username") private String username;

    static final String STRING_FORMAT = "User [Username = %s]";

    /**
     * Constructor for creating a new user
     *
     * @param username the username of user
     */
    public User(@JsonProperty("username") String username, @JsonProperty("type") String type){
        this.username = username;
        this.type = type;
    }

    /**
     * Acquire the username of user
     * @return the username of user
     */
    public String getUsername() {return this.username;}

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        } else {
            User other = (User) obj;
            return this.username.equals(other.getUsername());
        }
    }

    @JsonTypeName("helper")
    public static class Helper extends User{
        private List<Need> fundingBasket;

        /**
         * Constructor for creating a helper with a given username and funding basket
         * 
         * @param username the username of the helper
         */
        @JsonCreator
        public Helper(String username){
            super(username, "helper");
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

    @JsonTypeName("admin")
    public static class Admin extends User{
        private List<String> messageBoard;

        /**
         * Constructor for creating an admin with a given username and message board
         * 
         * @param username the username of the admin
         * @param messageBoard the message board (list of strings) where helper suggestions are stored
         * 
         */
        @JsonCreator
        public Admin(String username) {
            super(username, "admin");
            messageBoard = new ArrayList<>();
        }

        /**
         * Get the entire message board
         * @return the message board
         */
        public List<String> getMessageBoard() {
            return messageBoard;
        }

        /**
         * Add the message to the message board
         * @param message the message to add
         */
        public void getMessage(String message) {
            messageBoard.add(message);
        }

        /**
         * Delete the given message fromS the message board
         * @param message the message to delete
         */
        public void deleteMessage(String message) {
            messageBoard.remove(message);
        }
    }
    
}
