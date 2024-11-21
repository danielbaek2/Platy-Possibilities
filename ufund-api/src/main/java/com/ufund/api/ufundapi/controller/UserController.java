package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.persistence.UserDAO;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles REST API requests for the User resource
 */
@RestController
@RequestMapping("User")
public class UserController {
    private static final Logger LOG = Logger.getLogger(com.ufund.api.ufundapi.controller.UserController.class.getName());
    private UserDAO userDAO;

    /**
     * Creates a REST API controller for the user Requests
     *
     * @param userDAO The Helper Data Access Object which will perform CRUD operations
     */
    public UserController(@Qualifier("adminFileDAO") UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    /**
     * Responds to the GET request for all users with the given username
     *
     * @param username the username of the user to find
     *
     * @return ResponseEntity with list of helper objects and HTTP Status of OK if found,
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR if there is a problem
     */
    @GetMapping("/")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String username) {
        LOG.info("GET /User/?username=" + username);
        try {
            List<User> matchingUsers = userDAO.userSearch(username);
            return new ResponseEntity<>(matchingUsers, HttpStatus.OK);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
