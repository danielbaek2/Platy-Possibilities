package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;

public class Admin extends User {

    private List<String> messageBoard;

    @JsonCreator
    public Admin(String username) {
        super(username);
        messageBoard = new ArrayList<>();
    }

    public List<String> getMessageBoard() {
        return messageBoard;
    }

    public void getMessage(String message) {
        messageBoard.add(message);
    }

    public void deleteMessage(String message) {
        messageBoard.remove(message);
    }
}
