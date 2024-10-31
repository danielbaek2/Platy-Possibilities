package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles REST API requests for the User resource
 */
@RestController
@RequestMapping("User")
public abstract class UserController {
    private static final Logger LOG = Logger.getLogger(com.ufund.api.ufundapi.controller.UserController.class.getName());
    private UserDAO userDAO;

    /**
     * Creates a REST API controller for the user Requests
     *
     * @param userDAO The Helper Data Access Object which will perform CRUD operations
     */
    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;

    }

    // /**
    //  * Responds to the GET request for the funding basket of a particular helper
    //  * 
    //  * @param username The username of the helper requesting their funding basket
    //  * @return ResponseEntity with the basket (List of needs) and
    //  * HTTP status of OK if found, HTTP status of NOT_FOUND if not found and HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @GetMapping("/{username}/basket")
    // public ResponseEntity<List<Need>> getBasket(@PathVariable String username) {
    //     LOG.info("GET /Helper/" + username + "/basket");

    //     try {
    //         List<Need> basket = this.userDAO.getBasket(username);
    //         System.out.println(basket); // Temporary; display basket to monitor changes to it on the backend
    //         return serviceClass(basket, HttpStatus.NOT_FOUND);
    //     }
    //     catch(IOException e) {
    //         LOG.log(Level.SEVERE,e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // /**
    //  * Responds to the DELETE request on a need in the funding basket. Removes a given need from the funding basket.
    //  * 
    //  * @param username The username of the helper
    //  * @param need The need to remove from the funding basket
    //  * @return ResponseEntity with HTTP status of OK if removed usccessfully
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @DeleteMapping("/{username}/basket")
    // public ResponseEntity<Need> removeNeedFromBasket(@PathVariable String username, Need need) {
    //     LOG.info("DELETE /Helper/" + username + "/basket?id=" + need.getId());

    //     try {
    //         boolean delete = userDAO.removeNeedFromBasket(need, username);
    //         if (delete) {
    //             return new ResponseEntity<Need>(HttpStatus.OK);
    //         } else {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //     } catch (Exception e) {
    //         LOG.log(Level.SEVERE, e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    // /**
    //  * Responds to the PUT request on the funding basket. Adds a need to the funding basket.
    //  * 
    //  * @param username The username of the helper
    //  * @param need The need to add to the funding basket
    //  * @return ResponseEntity with HTTP status of OK if updated usccessfully
    //  * ResponseEntity with HTTP status of NOT_FOUND if not found
    //  * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
    //  */
    // @PutMapping("/{username}")
    // public ResponseEntity<Need> addNeedToBasket(@PathVariable String username, @RequestBody Need need) {
    //     LOG.info("PUT /Helper/" + username + "?id=" + need.getId());

    //     try {
    //         boolean add = userDAO.addNeedToBasket(need, username);
    //         if (add) {
    //             return new ResponseEntity<Need>(need, HttpStatus.OK);
    //         } else {
    //             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //         }
    //     } catch (Exception e) {
    //         LOG.log(Level.SEVERE, e.getLocalizedMessage());
    //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }

    @GetMapping("{/username}")
    public ResponseEntity<String> login(@PathVariable String username) {
        LOG.info("GET /User" + username);

        try {
            boolean exists = userDAO.verifyUser(username);
            if (exists) {
                boolean admin = userDAO.isAdmin(username);
                if (admin) {
                    return new ResponseEntity<String>("Admin", HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("Not Admin", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
