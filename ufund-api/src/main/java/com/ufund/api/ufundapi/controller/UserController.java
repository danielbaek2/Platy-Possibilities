package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.UserDAO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<Helper>> searchUsers(@RequestParam String username) {
        LOG.info("GET /Users/?username=" + username);

        try {
            List<Helper> matchingUsers = helperDAO.userSearch(username);
            if (matchingUsers.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(matchingUsers, HttpStatus.OK);
            }
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // curl.exe -X GET 'http://localhost:8080/Helper/johndoe/basket' //getBasket
    // curl.exe -X PUT -H 'Content-Type:application/json' 'http://localhost:8080/Helper/johndoe/basket' -d '{\"id\": 1,\"title\": \"Red panda Helping\",\"quantity\": 0,\"cost\": 0,\"quantityFunded\": 0,\"description\": null,\"quantity_funded\": 0}' //addNeedtoBasket
    // curl.exe -X DELETE -H 'Content-Type:application/json' 'http://localhost:8080/Helper/johndoe/basket' -d '{\"id\": 1,\"title\": \"Red panda Helping\",\"quantity\": 0,\"cost\": 0,\"quantityFunded\": 0,\"description\": null,\"quantity_funded\": 0}' //removeNeedtoBasket
    // LOGIN DOES NOT CURRENTLY WORK
}
