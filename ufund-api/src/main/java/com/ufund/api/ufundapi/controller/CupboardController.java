package com.ufund.api.ufundapi.controller;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ufund.api.ufundapi.persistence.CupboardDAO;
import com.ufund.api.ufundapi.model.Need;


@RestController
@RequestMapping("Cupboard")
public class CupboardController {
    private static final Logger LOG = Logger.getLogger(CupboardController.class.getName());
    private CupboardDAO boardDAO;

    /**
     * Creates a REST API controller for the Cupboard Requests
     * 
     * @param boardDAO The Cupboard Data Access Object which will perform CRUD operations
     *
     */
    public CupboardController(CupboardDAO boardDAO) {
        this.boardDAO = boardDAO;
    }














































































    /**
     * Helper method to return the correct object and HTTP status
     * 
     * @param <T> Generic parameter, allowing method to work with Need objects and arrays
     * @param input Need object/array to be returned
     * @param error_status The HTTP Status to be returned if the object is null
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
     * Responds to the GET request for all {@linkplain Need needs}
     * 
     * @return ResponseEntity with array of {@linkplain Need need} objects and 
     * HTTP status of OK if found, HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> GetNeeds() {
        LOG.info("GET/needs");

        try {
            Need[] needs = boardDAO.GetNeeds();
            return serviceClass(needs, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }
}
