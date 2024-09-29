package com.ufund.api.ufundapi.controller;

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
     * Create a need object using given need if it exists
     * 
     * @param need the need object to create
     * 
     * @return a Response Entity with the need and HttpStatus.Created if successful
     * @return a Response Entity with HttpStatus.INTERNAL_SERVER_ERROR if creation unsuccessful or IO exception occurred
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need){
        LOG.info("POST /Cupboard " + need);

        try{
            Need newNeed = boardDAO.createNeed(need); //try to create a need
            if(newNeed != null){
                return new ResponseEntity<Need>(newNeed, HttpStatus.CREATED);//return status created and need if successful
            }

            else{
                return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);//return status Server Error and if unsuccessful
            }
        }
        catch(IOException error){
            return new ResponseEntity<Need>(HttpStatus.INTERNAL_SERVER_ERROR);//return status Server Error and if IO exception occurred.
        }
    }

    /**
     * Updates the Need with the provided need object, if it exists
     *
     * @param need The need to update
     *
     * @return ResponseEntity with updated need object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /Cupboard " + need);

        // Replace below with your implementation
        try{
            Need uNeed = boardDAO.updateNeed(need);
            if (uNeed != null) {
                return new ResponseEntity<>(uNeed, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    /**
     * Responds to the GET request for all {@linkplain Need needs} whose title contains the test in the need_title
     * 
     * @param need_title needs containing this text are returned
     * 
     * @return ResponseEntity with array of {@link Need needs} objects and HTTP OK status
     * HTTP INTERNAL_SERVER_ERROR if there is a problem
     */
    @GetMapping("/")
    public ResponseEntity<Need[]> searchNeeds(@RequestParam String need_title) {
        LOG.info("GET /Cupboard/?title="+need_title);
        try{
            Need[] needs = boardDAO.searchNeeds(need_title);
            return new ResponseEntity<Need[]>(needs,HttpStatus.OK);
        }
        catch(IOException e){
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        LOG.info("GET /Cupboard");

        try {
            Need[] needs = boardDAO.getNeeds();
            return serviceClass(needs, HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }    
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /Cupboard/" + id);

        // Replace below with your implementation
        try {
            boolean delete = boardDAO.deleteNeed(id);
            if (delete) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }
    /**
     * Gets the Need with the provided int ID, if it exists
     *
     * @param id The ID of the Need to get
     *
     * @return ResponseEntity with gotten need object and HTTP status of OK if updated<br>
     * ResponseEntity with HTTP status of NOT_FOUND if not found<br>
     * ResponseEntity with HTTP status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("/{id}")
    public ResponseEntity<Need> getNeed(@PathVariable int id) {
        LOG.info("GET /Cupboard/" + id);
        try {
            Need need = boardDAO.getNeed(id);
            if (need != null)
                return new ResponseEntity<Need>(need,HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

//Testing Create : curl -X POST -H Content-Type:application/json http://localhost:8080/Cupboard -d "{\"title\": \"Releasing Pigeons\"}"
//Testing Get all needs : curl -X GET http://localhost:8080/Cupboard
//Testing Search for needs: curl -X GET http://localhost:8080/Cupboard/?title=Re
//Testing deleting a need: curl -i -X DELETE http://localhost:8080/Cupboard/<ID>   REPLACE ID
//Testing updating a need: curl -i -X PUT -H Content-Type:application/json http://localhost:8080/Cupboard -d "{\"id\": <ID>, \"title\": \"Releasing Wolves\"}" REPLACE ID
}
