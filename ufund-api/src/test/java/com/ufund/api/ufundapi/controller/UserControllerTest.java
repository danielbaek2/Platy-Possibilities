package com.ufund.api.ufundapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.persistence.UserDAO;

@Tag("Controller-Tier")
public class UserControllerTest {
    private UserController userController;
    private UserDAO mockUserDAO;
    private Helper mockHelper;

    @BeforeEach
    void setUpHelperController() {
        mockUserDAO = mock(UserDAO.class);
        userController = new UserController(mockUserDAO);
        mockHelper = mock(Helper.class);
    }

    @Test
    void testSearchUser() throws IOException {
        String username = "Jane";

        when(mockUserDAO.userSearch(username)).thenReturn(Arrays.asList(mockHelper));

        ResponseEntity<List<Helper>> response = userController.searchUsers(username);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
