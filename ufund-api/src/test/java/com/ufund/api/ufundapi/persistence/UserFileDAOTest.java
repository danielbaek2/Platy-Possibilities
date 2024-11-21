package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User;
import com.ufund.api.ufundapi.model.User.Helper;




@Tag("Persistence-Tier")
public class UserFileDAOTest {
    UserFileDAO userFileDAO;
    Helper[] testHelperList;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setUpUserFileDAO() throws IOException{
        testHelperList = new Helper[2];
        testHelperList[0] = new Helper("Steve Irwin");
        testHelperList[1] = new Helper("Uncle Ben");
        mockObjectMapper = mock(ObjectMapper.class);

        when(mockObjectMapper.readValue(new File("test_file.txt"), Helper[].class)).thenReturn(testHelperList);

        userFileDAO = new TestFileDao("test_file.txt", mockObjectMapper);
    }

    @Test
    void testVerifyUserTrue() throws IOException{
        boolean expected = true;
        boolean actual = userFileDAO.verifyUser("Steve Irwin");
        assertEquals(expected, actual);
    }

    @Test
    void testVerifyUserFalse() throws IOException{
        boolean expected = false;
        boolean actual = userFileDAO.verifyUser("Bob");
        assertEquals(expected, actual);
    }

    @Test
    void testIsAdminTrue() throws IOException{
        boolean expected = true;
        boolean actual = userFileDAO.isAdmin("Admin");
        assertEquals(expected, actual);
    }

    @Test
    void testIsAdminFalse() throws IOException{
        boolean expected = false;
        boolean actual = userFileDAO.isAdmin("Steve Irwin");
        assertEquals(expected, actual);
    }

    @Test
    void testUserSearch() throws IOException{
        assertEquals(1, userFileDAO.userSearch("Steve Irwin").size());
    }
}
