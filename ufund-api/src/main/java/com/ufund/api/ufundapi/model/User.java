package com.ufund.api.ufundapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class representing a User
 */
public class User {

    @JsonProperty("username") private String username;
    @JsonProperty("password") private String password;

    //To string format
    static final String STRING_FORMAT = "User [Username = %s, Password = %s]";

    /**
     * Constructor for creating a new user
     *
     * @param username
     * @param password the title of the need
     *
     */
    public User(@JsonProperty("username") String password, @JsonProperty("username") String username){
        this.username = username;
        this.password = password;
    }

    /**
     * Acquire the username of user
     * @return the username of user
     */
    public String gedUsername() {return this.username;}

    /**
     * Acquire the password of user
     * @return the password of user
     */
    public String getPassword() {return this.password;}

    @Override
    public String toString() {
        return String.format(STRING_FORMAT, username, password);
    }
}

