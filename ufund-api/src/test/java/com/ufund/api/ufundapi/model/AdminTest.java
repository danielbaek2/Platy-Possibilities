package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.ufund.api.ufundapi.model.User.Admin;

import java.util.List;

@Tag("Model-Tier")
public class AdminTest {
    @Test
    void testCreateAdmin() {
        String testUsername = "TestUsername";
        Admin admin = new Admin(testUsername);
        assertEquals(testUsername, admin.getUsername());
    }

    @Test
    void testGetMessageBoard() {
        String testUsername = "TestUsername";
        Admin admin = new Admin(testUsername);
        int expectedBoardSize = 0;
        List<String> board = admin.getMessageBoard();
        assertEquals(expectedBoardSize, board.size());
    }

    @Test
    void testGetMessage() {
        String testUsername = "TestUsername";
        String testMessage = "Test";
        Admin admin = new Admin(testUsername);
        int expectedBoardSize = 1;
        admin.getMessage(testMessage);
        List<String> board = admin.getMessageBoard();
        assertEquals(expectedBoardSize, board.size());
    }

    @Test
    void testDeleteMessage() {
        String testUsername = "TestUsername";
        String testMessage = "Test";
        Admin admin = new Admin(testUsername);
        int expectedBoardSize = 0;
        admin.getMessage(testMessage);
        admin.deleteMessage(testMessage);
        List<String> board = admin.getMessageBoard();
        assertEquals(expectedBoardSize, board.size());
    }
}
