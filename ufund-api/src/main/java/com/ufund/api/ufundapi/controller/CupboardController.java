
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

/**
 * Handles the REST API requests for the Need resource
 */
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
     * Creates a need object using given need if it exists
     *
     * @param need the need object to create
     *
     * @return a Response Entity with the need and HttpStatus.Created if successful
     * @return a Response Entity with HttpStatus.INTERNAL_SERVER_ERROR if creation unsuccessful or IO exception occurred
     */
    @PostMapping("")
    public ResponseEntity<Need> createNeed(@RequestBody Need need){
        LOG.info("POST /Cupboard " + need);
        try {
            if(boardDAO.searchNeeds(need.getTitle()).length > 0){return new ResponseEntity<>(HttpStatus.CONFLICT);} //checking for unique title
            Need nNeed = boardDAO.createNeed(need);
            return new ResponseEntity<>(nNeed, HttpStatus.CREATED);
        } catch (IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Updates the Need with the provided need object, if it exists
     *
     * @param need The need to update
     *
     * @return ResponseEntity with updated need object and HTTP Status of OK if updated
     * ResponseEntity with HTTP Status of NOT_FOUND if not found
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @PutMapping("")
    public ResponseEntity<Need> updateNeed(@RequestBody Need need) {
        LOG.info("PUT /Cupboard " + need);
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
     * @return ResponseEntity with array of {@link Need needs} objects and HTTP Status of OK if found,
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR if there is a problem
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
     * Responds to the GET request for all {@linkplain Need needs}
     *
     * @return ResponseEntity with array of {@linkplain Need need} objects and HTTP Status of OK if found, 
     * ResponseEntity with HTTP Status of NOT_FOUND if not found, or 
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
     */
    @GetMapping("")
    public ResponseEntity<Need[]> getNeeds() {
        LOG.info("GET /Cupboard");
        try {
            Need[] needs = boardDAO.getNeeds();
            if (needs != null)
                return new ResponseEntity<Need[]>(needs, HttpStatus.OK);
            else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch(IOException e) {
            LOG.log(Level.SEVERE,e.getLocalizedMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the Need with the provided int ID, if it exists
     * 
     * @param id The ID of the Need to delete
     * @return ResponseEntity with HTTP Status of OK if deleted
     * ResponseEntity with HTTP Status of NOT_FOUND if not found
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise 
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Need> deleteNeed(@PathVariable int id) {
        LOG.info("DELETE /Cupboard/" + id);
        try {
            boolean delete = boardDAO.deleteNeed(id);
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
     * Gets the Need with the provided int ID, if it exists
     *
     * @param id The ID of the Need to get
     *
     * @return ResponseEntity with the retrieved need object and HTTP Status of OK if updated
     * ResponseEntity with HTTP Status of NOT_FOUND if not found
     * ResponseEntity with HTTP Status of INTERNAL_SERVER_ERROR otherwise
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
//Testing Get one need : curl -X GET http://localhost:8080/Cupboard/2
//Testing Search for needs: curl -X GET http://localhost:8080/Cupboard/?need_title=Re
//Testing deleting a need: curl -i -X DELETE http://localhost:8080/Cupboard/<ID>   REPLACE ID
//Testing updating a need: curl -i -X PUT -H Content-Type:application/json http://localhost:8080/Cupboard -d "{\"id\": <ID>, \"title\": \"Releasing Wolves\"}" REPLACE ID
}
