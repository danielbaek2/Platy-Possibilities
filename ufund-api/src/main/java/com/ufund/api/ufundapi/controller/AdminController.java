package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Need;
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
     * Helper method to return the correct object and HTTP status
     *
     * @param <T> Generic parameter, allowing method to work with Need objects and arrays
     * @param input Need object/array to be returned
     * @param errorStatus The HTTP Status to be returned if the object is null
     * @return ResponseEntity with the {@linkplain Need need} object or array and 
     * HTTP status of OK if found, HTTP status of error_status if not found or if there is a conflict
     */
    private <T> ResponseEntity<T> serviceClass(T input, HttpStatus errorStatus){
        if (input != null)
            return new ResponseEntity<T>(input,HttpStatus.OK);
        else
            return new ResponseEntity<>(errorStatus);
    }

    @GetMapping("/board")
    public ResponseEntity<List<String>> getMessageBoard() {
        LOG.info("GET /Admin/board");
        try {
            List<String> messageBoard = adminDAO.getMessageBoard();
            return serviceClass(messageBoard, HttpStatus.OK);
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // @PutMapping("/board")
    // public ResponseEntity<String> getMessage(@RequestBody String message) {
    //     LOG.info("PUT /Admin/board/" + message);
    //     try {
    //         boolean get = adminDAO.getMessage(message);
    //         if (get) {
    //             return new ResponseEntity<String>(message, HttpStatus.OK);
    //         } else {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //     }
    //     catch (IOException e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

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