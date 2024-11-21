package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User.Admin;

import java.io.File;
import java.io.IOException;

@Tag("Persistence-Tier")
public class AdminFileDAOTest {
    AdminFileDAO adminFileDAO;
    ObjectMapper mockObjectMapper;
    Admin testAdmin;

    @BeforeEach
    void setupAdminFileDAO() throws IOException {
        testAdmin = new Admin("Test");
        mockObjectMapper = mock(ObjectMapper.class);
        when(mockObjectMapper.readValue(new File("test_file.txt"), Admin.class)).thenReturn(testAdmin);
        adminFileDAO = new AdminFileDAO("test_file.txt", mockObjectMapper);
    }

    @Test
    void testDeleteMessage() throws IOException {
        adminFileDAO.getMessage("Test");
        boolean expected = true;
        boolean actual = adminFileDAO.deleteMessage("Test");
        assertEquals(expected, actual);
    }

    @Test
    void testDeleteMessageNotFound() throws IOException {
        boolean expected = false;
        boolean actual = adminFileDAO.deleteMessage("Test");
        assertEquals(expected, actual);
    }

    @Test
    void testGetMessage() throws IOException {
        boolean expected = true;
        int expectedNum = 1;
        boolean actual = adminFileDAO.getMessage("Test");
        int actualNum = adminFileDAO.getMessageBoard().size();
        assertEquals(expected, actual);
        assertEquals(expectedNum, actualNum);
    }
}
