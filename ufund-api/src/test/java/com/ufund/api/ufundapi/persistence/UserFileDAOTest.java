package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;

@Tag("Persistence-Tier")
public class UserFileDAOTest {
    UserFileDAO helperFileDAO;
    Helper[] testHelperList;
    Need testNeeds;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setupHelperFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testHelperList = new Helper[2];
        testHelperList[0] = new Helper("Steve Irwin");
        testHelperList[1] = new Helper("Uncle Ben");

        testNeeds = new Need(1,"this could be a test");

        when(mockObjectMapper.readValue(new File("test_file.txt"), Helper[].class)).thenReturn(testHelperList);
		helperFileDAO = new UserFileDAO("test_file.txt", mockObjectMapper);
    }

    @Test
    void testRemoveNeedFromBasket() throws IOException{
        helperFileDAO.addNeedToBasket(testNeeds, "Steve Irwin");

        boolean expected = true;
        boolean actual = helperFileDAO.removeNeedFromBasket(testNeeds,"Steve Irwin");
        assertEquals(expected, actual);
    }

    @Test
    void testRemoveNeedFromBasketNotFound() throws IOException{
        boolean expected = false;
        boolean actual = helperFileDAO.removeNeedFromBasket(testNeeds, "Steve Irwin");
        assertEquals(expected,actual);
    }

    @Test
    void testAddNeedToBasket() throws IOException{
        boolean expected = true;
        int expectedNum = 1;
        boolean actual = helperFileDAO.addNeedToBasket(testNeeds, "Steve Iwrin");
        int actualNum = helperFileDAO.getBasket("Steve Irwin").size();

        assertEquals(expected, actual);
        assertEquals(expectedNum, actualNum);
    }

    @Test
    void testAddNeedToBasketDupelicate() throws  IOException{
        boolean expected = false;
        int expectedNum = 1;
        helperFileDAO.addNeedToBasket(testNeeds, "Steve Iwrin");
        boolean actual = helperFileDAO.addNeedToBasket(testNeeds, "Steve Iwrin");
        int actualNum = helperFileDAO.getBasket("Steve Irwin").size();

        assertEquals(expected, actual);
        assertEquals(expectedNum, actualNum);
    }

    @Test
    void testGetBasket() throws IOException{
        
    }
}
