package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

import com.ufund.api.ufundapi.persistence.UserFileDAO;
import com.ufund.api.ufundapi.model.User;

@RestController
@RequestMapping("Helper")
public class UserController {
    private static final Logger LOG = Logger.getLogger(com.ufund.api.ufundapi.controller.UserController.class.getName());
    private UserDAO helperDAO;

    /**
     * Creates a REST API controller for the user Requests
     *
     * @param helperDAO The Helper Data Access Object which will perform CRUD operations
     */
    public UserController(UserDAO helperDAO) {
        this.helperDAO = helperDAO;

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

    /**
     * Responds to the GET request for the funding basket of a particular helper
     * 
     * @param username The username of the helper requesting their funding basket
     * @return ResponseEntity with the basket (List of needs) and
     * HTTP status of OK if found, HTTP status of NOT_FOUND if not found and HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}/basket")
    public ResponseEntity<List<Need>> getBasket(@PathVariable String username) {
        LOG.info("GET /Helper" + username + "/basket");

        try {
            List<Need> basket = this.helperDAO.getBasket(username);
            return serviceClass(basket, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request on a need in the funding basket. Removes a given need from the funding basket.
     * 
     * @param username The username of the helper
     * @param need The need to remove from the funding basket
     * @return ResponseEntity with HTTP status of OK if removed usccessfully
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}/basket")
    public ResponseEntity<Need> removeNeedFromBasket(@PathVariable String username, @RequestBody Need need) {
        LOG.info("/Helper/" + username + "/DELETE" + need);

        try {
            boolean delete = helperDAO.removeNeedFromBasket(need, username);
            if (delete) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request on the funding basket. Adds a need to the funding basket.
     * 
     * @param username The username of the helper
     * @param need The need to add to the funding basket
     * @return ResponseEntity with HTTP status of OK if updated usccessfully
     * ResponseEntity with HTTP status of NOT_FOUND if not found
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{username}/basket")
    public ResponseEntity<Need> addNeedToBasket(@PathVariable String username, @RequestBody Need need) {
        LOG.info("/Helper/" + username + "/DELETE" + need);

        try {
            boolean add = helperDAO.addNeedToBasket(need, username);
            if (add) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
