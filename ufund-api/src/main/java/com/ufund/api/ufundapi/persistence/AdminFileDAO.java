package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AdminFileDAO implements AdminDAO {
    private String filename;
    private ObjectMapper objectMapper = null;
    private Admin admin;    

    public AdminFileDAO(@Value("data/admin.json") String filename, ObjectMapper objectmapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectmapper;
        loadFile();
    }

    private void loadFile() throws IOException {
        this.admin = objectMapper.readValue(new File(filename), Admin.class);
    }

    private void saveFile() throws IOException {
        objectMapper.writeValue(new File(this.filename), admin);
    }

    public boolean deleteMessage(String message) throws IOException {
        synchronized (admin) {
            List<String> messageBoard = admin.getMessageBoard();
            if (messageBoard.contains(message)) {
                admin.deleteMessage(message);
                saveFile();
                return true;
            }
            return false;
        }
    }

    public boolean getMessage(String message) throws IOException {
        synchronized (admin) {
            List<String> messageBoard = admin.getMessageBoard();
            if (messageBoard.contains(message)) {
                return false;
            } else {
                admin.getMessage(message);
                saveFile();
                return true;
            }
        }
    }

    public List<String> getMessageBoard() throws IOException {
        return admin.getMessageBoard();
    }
}
