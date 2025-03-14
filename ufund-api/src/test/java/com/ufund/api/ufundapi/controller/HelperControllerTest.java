package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.model.MessageBoard;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.HelperDAO;

/**
 * Test the Helper Controller class
 */
@Tag("Controller-Tier")
public class HelperControllerTest {
    private HelperController helperController;
    private HelperDAO mockHelperDAO;
    private Need mockNeed;
    private MessageBoard mockMessageBoard;

    /**
     * Before each test, create a new HelperController object and inject a mock Helper DAO
     */
    @BeforeEach
    void setUpHelperController() {
        mockHelperDAO = mock(HelperDAO.class);
        mockMessageBoard = mock(MessageBoard.class);
        mockNeed = mock(Need.class);
        helperController = new HelperController(mockHelperDAO, mockMessageBoard);
    }

    @Test
    void testGetBasket() throws IOException {
        String username = "Jane";
        List<Need> mockBasket = Arrays.asList(mockNeed);
        when(mockHelperDAO.getBasket(username)).thenReturn(mockBasket);
        ResponseEntity<List<Need>> response = helperController.getBasket(username);

        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(mockBasket,response.getBody());
    }

    @Test
    void testGetBasketNotFound() throws IOException {
        // Setup
        String username = "Jane";
        // when getBasket is called on the mock helper DAO, return null simulating failed retrieval
        when(mockHelperDAO.getBasket(username)).thenReturn(null);

        // Invoke
        ResponseEntity<List<Need>> response = helperController.getBasket(username);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetBasketExceptionHandling() throws IOException {
        // Setup
        String username = "Jane";
        // when getBasket is called, throw an IOException
        doThrow(new IOException()).when(mockHelperDAO).getBasket(username);

        // Invoke
        ResponseEntity<List<Need>> response = helperController.getBasket(username);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
    }

    @Test
    void testRemoveNeedFromBasket() throws  IOException{
        String username = "Jane";

        when(mockHelperDAO.removeNeedFromBasket(mockNeed,username)).thenReturn(true);
        List<Need> mockBasket = Arrays.asList(mockNeed);
        when(mockHelperDAO.getBasket(username)).thenReturn(mockBasket);
        ResponseEntity<Need> response = helperController.removeNeedFromBasket(username, mockNeed.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testRemoveNeedFromBasketNotFound() throws IOException {
        // Setup
        String username = "Jane";
        // when removeFromBasket is called return null simulating failed removal
        when(mockHelperDAO.removeNeedFromBasket(mockNeed, username)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = helperController.removeNeedFromBasket(username, mockNeed.getId());

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());    
    }

    @Test
    void testRemoveNeedFromBasketExceptionHandling() throws IOException {
        // Setup
        String username = "Jane";
        // when removeNeedFromBasket is called, throw an IOException
        doThrow(new IOException()).when(mockHelperDAO).removeNeedFromBasket(mockNeed, username);
        List<Need> mockBasket = Arrays.asList(mockNeed);
        when(mockHelperDAO.getBasket(username)).thenReturn(mockBasket);

        // Invoke
        ResponseEntity<Need> response = helperController.removeNeedFromBasket(username, mockNeed.getId());

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
    }

    @Test
    void testRemoveNeedRemoveFail() throws IOException{
        String username = "Jane";

        when(mockHelperDAO.removeNeedFromBasket(mockNeed,username)).thenReturn(false);
        List<Need> mockBasket = Arrays.asList(mockNeed);
        when(mockHelperDAO.getBasket(username)).thenReturn(mockBasket);
        ResponseEntity<Need> response = helperController.removeNeedFromBasket(username, mockNeed.getId());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddNeedToBasket() throws  IOException{
        String username = "Jane";

        when(mockHelperDAO.addNeedToBasket(mockNeed,username)).thenReturn(true);
        ResponseEntity<Need> response = helperController.addNeedToBasket(username, mockNeed);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddNeedToBasketNotFound() throws IOException {
        // Setup
        String username = "Jane";
        // when addNeedToBasket is called return null simulating failed insertion and save
        when(mockHelperDAO.addNeedToBasket(mockNeed, username)).thenReturn(false);

        // Invoke
        ResponseEntity<Need> response = helperController.addNeedToBasket(username, mockNeed);

        // Analyze
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());  
    }

    @Test
    void testAddNeedToBasketExceptionHandling() throws IOException {
        // Setup
        String username = "Jane";
        // when addNeedToBasket is called, throw an IOException
        doThrow(new IOException()).when(mockHelperDAO).addNeedToBasket(mockNeed, username);

        // Invoke
        ResponseEntity<Need> response = helperController.addNeedToBasket(username, mockNeed);

        // Analyze
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());  
    }

    @Test
    void testCheckout() throws IOException {
        String username = "Jane";

        when(mockHelperDAO.removeNeedFromBasket(mockNeed,username)).thenReturn(true);
        List<Need> mockBasket = Arrays.asList(mockNeed);
        when(mockHelperDAO.getBasket(username)).thenReturn(mockBasket);

        ResponseEntity<List<Need>> response = helperController.checkoutBasket(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());

    }

    @Test
    void testCheckoutException() throws IOException {
        String username = "Jane";

        when(mockHelperDAO.removeNeedFromBasket(mockNeed,username)).thenReturn(true);

        when(mockHelperDAO.getBasket(username)).thenThrow(new IOException());

        ResponseEntity<List<Need>> response = helperController.checkoutBasket(username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }

    @Test
    void testAddMessage() throws IOException{
        String username = "Jane";
        String message = "Test Message";

        when(mockMessageBoard.addMessage(message)).thenReturn(true);

        ResponseEntity<String> response = helperController.addMessage(message, username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testAddMessageNotFound() throws IOException{
        String username = "Jane";
        String message = "Test Message";

        when(mockMessageBoard.addMessage(message)).thenReturn(false);

        ResponseEntity<String> response = helperController.addMessage(message, username);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddMessageException() throws IOException{
        String username = "Jane";
        String message = "Test Message";

        when(mockMessageBoard.addMessage(message)).thenThrow(new IOException());

        ResponseEntity<String> response = helperController.addMessage(message, username);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}