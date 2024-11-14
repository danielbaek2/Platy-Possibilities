package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ufund.api.ufundapi.persistence.AdminDAO;

@Tag("Controller-Tier")
public class AdminControllerTest {
    private AdminController adminController;
    private AdminDAO mockAdminDAO;
    private List<String> messageBoard = new ArrayList<>();

    /**
     * Before each test, create a new AdminController object and inject a mock Admin DAO
     */
    @BeforeEach
    void setUpHelperController() {
        mockAdminDAO = mock(AdminDAO.class);
        messageBoard.add("this is a message");
        adminController = new AdminController(mockAdminDAO);
    }

    @Test
    void testGetMessageBoard() throws IOException{
        when(mockAdminDAO.getMessageBoard()).thenReturn(messageBoard);
        ResponseEntity<List<String>> expected = new ResponseEntity<>(messageBoard, HttpStatus.OK);
        assertEquals(expected, adminController.getMessageBoard());
    }

    @Test
    void testGetMessageBoardNotFound() throws IOException{
        when(mockAdminDAO.getMessageBoard()).thenReturn(null);
        ResponseEntity<List<String>> expected = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        assertEquals(expected, adminController.getMessageBoard());
    }

    @Test
    void testGetMessageBoardExceptionHandling() throws IOException{
        doThrow(new IOException()).when(mockAdminDAO).getMessageBoard();
        ResponseEntity<List<String>> response = adminController.getMessageBoard();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteMessage() throws IOException{
        String messageToDelete = "this is a message";
        when(mockAdminDAO.deleteMessage(messageToDelete)).thenReturn(true);
        ResponseEntity<String> expected = new ResponseEntity<>("Message deleted.", HttpStatus.OK);
        assertEquals(expected, adminController.deleteMessage(messageToDelete));
    }

    @Test
    void testDeleteMessageNotFound() throws IOException{
        String messageToDelete = "message";
        when(mockAdminDAO.deleteMessage(messageToDelete)).thenReturn(false);
        ResponseEntity<String> expected = new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
        assertEquals(expected, adminController.deleteMessage(messageToDelete));
    }

    @Test
    void testDeleteMessageExceptionHandling() throws IOException{
        String messageToDelete = "message";
        doThrow(new IOException()).when(mockAdminDAO).deleteMessage(messageToDelete);
        ResponseEntity<String> response = adminController.deleteMessage(messageToDelete);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
