package com.ufund.api.ufundapi.controller;

import com.ufund.api.ufundapi.persistence.HelperDAO;
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

import com.ufund.api.ufundapi.persistence.HelperFileDAO;
import com.ufund.api.ufundapi.model.User;

@RestController
@RequestMapping("Cupboard")
public class HelperController {
        private static final Logger LOG = Logger.getLogger(com.ufund.api.ufundapi.controller.HelperController.class.getName());
        private HelperDAO userDAO;

        /**
         * Creates a REST API controller for the user Requests
         *
         * @param userDAO The User Data Access Object which will perform CRUD operations
         */
        public HelperController(HelperDAO userDAO) {
            this.userDAO = userDAO;
        }

    }
