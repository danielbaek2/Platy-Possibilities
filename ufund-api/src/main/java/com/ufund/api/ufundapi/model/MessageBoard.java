package com.ufund.api.ufundapi.model;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ufund.api.ufundapi.persistence.AdminDAO;

@Component
public class MessageBoard {
    private final AdminDAO adminDAO;

    @JsonCreator
    public MessageBoard(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public List<String> getMessageBoard() throws IOException {
        return adminDAO.getMessageBoard();
    }

    public boolean addMessage(String message) throws IOException {
        return adminDAO.getMessage(message);
    }
    
    public boolean deleteMessage(String message) throws IOException {
        return adminDAO.deleteMessage(message);
    }
}
