package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.persistence.AdminDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

/**
 * Handles the REST API requests for the Admin resource
 */
@RestController
@RequestMapping("Admin")
public class AdminController {
    private static final Logger LOG = Logger.getLogger(AdminController.class.getName());
    private AdminDAO adminDAO;

    /**
     * Creates a REST API controller for the Admin Requests
     *
     * @param adminDAO The Admin Data Access Object which will perform CRUD operations
     *
     */
    public AdminController(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    /**
     * Retrieves the message board
     * 
     * @return ResponseEntity with the message board and HTTP Status of OK if successful,
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or 
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/board")
    public ResponseEntity<List<String>> getMessageBoard() {
        LOG.info("GET /Admin/board");
        try {
            List<String> messageBoard = adminDAO.getMessageBoard();
            if (messageBoard != null)
                return new ResponseEntity<List<String>>(messageBoard, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the given message from the message board, if it exists
     * 
     * @param message The message to be deleted
     * @return ResponseEntity with HTTP Status of OK if deleted,
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or 
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/board")
    public ResponseEntity<String> deleteMessage(@RequestBody String message) {
        LOG.info("DELETE /Admin/board/" + message);
        try {
            boolean delete = adminDAO.deleteMessage(message);
            if (delete) {
                return new ResponseEntity<>("Message deleted.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // getMessageBoard(): curl.exe -X GET 'http://localhost:8080/Admin/board'
    // getMessage(): curl.exe -i -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/Admin/board' -d "Add message here."
    // deleteMessage: curl.exe -i -X DELETE 'http://localhost:8080/Admin/board' -d "Add message here."
}