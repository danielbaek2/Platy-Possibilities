package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ufund.api.ufundapi.model.Admin;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Implements the functionality for JSON file-based persistence for Admin
 */
@Component
public class AdminFileDAO implements AdminDAO {
    private String filename;
    private ObjectMapper objectMapper = null;
    private Admin admin;    

    /**
     * AdminFileDAO - Current instance of the admin data access object.
     *
     * @param filename - The name of the file to be loaded.
     * @param objectmapper - The object mapper.
     */
    public AdminFileDAO(@Value("ufund-api/data/admin.json") String filename, ObjectMapper objectmapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectmapper;
        loadFile();
    }

    /**
     *  
     * @throws IOException if there is an issue with the data storage
     */
    private void loadFile() throws IOException {
        this.admin = objectMapper.readValue(new File(filename), Admin.class);
    }

    /**
     * Saves the file to the hashmap
     * @throws IOException if there is an issue with the data storage
     */
    private void saveFile() throws IOException {
        objectMapper.writeValue(new File(this.filename), admin);
    }

    /**
     * Deletes the message if it exists on the message board.
     * @param message - The message to be removed.
     * @return - True if message is in the message board, false if not.
     * @throws IOException if there is an issue with the data storage
     */
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

    /**
     * Add the message to the message board if it does not already exist.
     * @param message - The message to add.
     * @return - True if message is not in the message board, false if it is.
     * @throws IOException if there is an issue with the data storage
     */
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

    /** 
     * Retrieves the entire message board.
     * @return - The message board (list of strings), could be empty.
     * @throws IOException if there is an issue with the data storage
     */
    public List<String> getMessageBoard() throws IOException {
        return admin.getMessageBoard();
    }
}
