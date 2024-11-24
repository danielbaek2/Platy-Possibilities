package com.ufund.api.ufundapi.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufund.api.ufundapi.model.User.Helper;
import com.ufund.api.ufundapi.model.MessageBoard;
import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;

@Tag("Persistence-Tier")
public class HelperFileDAOTest {
    HelperFileDAO helperFileDAO;
    Helper[] testHelperList;
    Need testNeeds;
    ObjectMapper mockObjectMapper;
    MessageBoard mockMessageBoard;

    @BeforeEach
    void setupHelperFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        mockMessageBoard = mock(MessageBoard.class);
        testHelperList = new Helper[2];
        testHelperList[0] = new Helper("Steve Irwin");
        testHelperList[1] = new Helper("Uncle Ben");

        testNeeds = new Need(1,"this could be a test",null,5,50);

        when(mockObjectMapper.readValue(new File("test_file.txt"), User[].class)).thenReturn(testHelperList);
		helperFileDAO = new HelperFileDAO("test_file.txt", mockObjectMapper, mockMessageBoard);
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
        boolean actual = helperFileDAO.addNeedToBasket(testNeeds, "Steve Irwin");
        int actualNum = helperFileDAO.getBasket("Steve Irwin").size();

        assertEquals(expected, actual);
        assertEquals(expectedNum, actualNum);
    }

    @Test
    void testAddNeedToBasketDuplicate() throws  IOException{
        boolean expected = false;
        int expectedNum = 1;
        helperFileDAO.addNeedToBasket(testNeeds, "Steve Irwin");
        boolean actual = helperFileDAO.addNeedToBasket(testNeeds, "Steve Irwin");
        int actualNum = helperFileDAO.getBasket("Steve Irwin").size();

        assertEquals(expected, actual);
        assertEquals(expectedNum, actualNum);
    }

    @Test
    void testGetBasketInvalidUser() throws IOException{
        List<Need> actual = helperFileDAO.getBasket("Invalid user");
        assertNull(actual);
    }

    @Test
    void testAddMessageInvalidUser() throws IOException{
        boolean actual = helperFileDAO.addMessage("", "Invalid User");
        assertFalse(actual);
    }

    @Test
    void testAddMessageRepeatMessage() throws IOException{
        ArrayList<String> list = new ArrayList<String>();
        list.add("Test");
        when(mockMessageBoard.getMessageBoard()).thenReturn(list);
        boolean actual = helperFileDAO.addMessage("Test", "Steve Irwin");
        assertFalse(actual);
    }

    @Test
    void testAddMessage() throws IOException{
        ArrayList<String> list = new ArrayList<String>();
        when(mockMessageBoard.getMessageBoard()).thenReturn(list);
        boolean actual = helperFileDAO.addMessage("Test", "Steve Irwin");
        assertTrue(actual);
    }




}