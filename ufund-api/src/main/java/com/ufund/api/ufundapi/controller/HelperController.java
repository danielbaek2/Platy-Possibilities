package com.ufund.api.ufundapi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufund.api.ufundapi.model.MessageBoard;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.HelperDAO;

/**
 * Handles the REST API requests for the Helper resource
 */
@RestController
@RequestMapping("Helper")
public class HelperController{
    private static final Logger LOG = Logger.getLogger(com.ufund.api.ufundapi.controller.UserController.class.getName());
    private HelperDAO helperDAO;
    private MessageBoard messageBoard;

    /**
     * Creates a REST API controller for the Helper Requests
     *
     * @param helperDAO The Helper Data Access Object which will perform CRUD operations
     */
    public HelperController(HelperDAO helperDAO, MessageBoard messageBoard) {
        this.helperDAO = helperDAO;
        this.messageBoard = messageBoard;
    }

    /**
     * Responds to the GET request for the funding basket of a particular helper
     * 
     * @param username The username of the helper requesting their funding basket
     * @return ResponseEntity with the basket (List of needs) and HTTP Status of OK if found, 
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{username}/basket")
    public ResponseEntity<List<Need>> getBasket(@PathVariable String username) {
        LOG.info("GET /Helper/" + username + "/basket");

        try {
            List<Need> basket = this.helperDAO.getBasket(username);
            if (basket != null){
                return new ResponseEntity<List<Need>>(basket, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request on a need in the funding basket.
     * Removes a given need from the funding basket.
     * 
     * @param username The username of the helper
     * @param need The need to remove from the funding basket
     * @return ResponseEntity with HTTP Status of OK if removed successfully
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}/basket")
    public ResponseEntity<Need> removeNeedFromBasket(@PathVariable String username, int id) {
        LOG.info("DELETE /Helper/" + username + "/basket?id=" + id);
        try {
            Need need = null;
            for(Need n : helperDAO.getBasket(username)){
                if (n.getId() == id){
                    need = n;
                }
            }
            if(need != null){
                boolean delete = helperDAO.removeNeedFromBasket(need, username);
                if (delete) {
                    return new ResponseEntity<Need>(HttpStatus.OK);
                } 
                else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
            else {
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
     * @return ResponseEntity with the need and HTTP Status of OK if added successfully
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{username}")
    public ResponseEntity<Need> addNeedToBasket(@PathVariable String username, @RequestBody Need need) {
        LOG.info("PUT /Helper/" + username + "?id=" + need.getId());

        try {
            boolean add = helperDAO.addNeedToBasket(need, username);
            if (add) {
                return new ResponseEntity<Need>(need, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the DELETE request. Checks out all needs in the basket.
     * 
     * @param username The username of the helper 
     * @return ResponseEntity with the emptied basket and HTTP Status of OK if checked out successfully
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @DeleteMapping("/{username}/checkout")
    public ResponseEntity<List<Need>> checkoutBasket(@PathVariable String username){
        LOG.info("DELETE /Helper/" + username + "/checkout");

        try {
            List<Need> basket = this.helperDAO.getBasket(username);
            List<Need> basketCopy = new ArrayList<Need>(basket);
            for(Need need:basketCopy){
                need.fundNeed();
                this.helperDAO.removeNeedFromBasket(need, username);
            }
            return new ResponseEntity<List<Need>>(basketCopy,HttpStatus.OK);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Responds to the PUT request on the message board. Adds a message to the message board.
     * 
     * @param message The message to be added
     * @param username The username of the helper posting the message
     * @return ResponseEntity with the message and HTTP Status of OK if added successfully
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("/{username}/board")
    public ResponseEntity<String> addMessage(@RequestBody String message, @PathVariable String username) {
        LOG.info("PUT /Helper/board/" + message);
        try {
            boolean get = messageBoard.addMessage(message);
            if (get) {
                return new ResponseEntity<String>(message, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
