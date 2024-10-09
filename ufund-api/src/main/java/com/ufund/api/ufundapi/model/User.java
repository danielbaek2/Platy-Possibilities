package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a User
 */
public abstract class User {

    @JsonProperty("username") private String username;
    //@JsonProperty("password") private String password;

    //To string format
    static final String STRING_FORMAT = "User [Username = %s]";

    /**
     * Constructor for creating a new user
     *
     * @param username the username of user
     *
     */
    public User(@JsonProperty("username") String username){
        this.username = username;
     //   this.password = password;
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
}

