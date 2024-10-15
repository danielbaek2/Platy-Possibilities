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
public class HelperFileDAOTest {
    HelperFileDAO helperFileDAO;
    Helper[] testHelperList;
    ObjectMapper mockObjectMapper;

    @BeforeEach
    void setupHelperFileDAO() throws IOException{
        mockObjectMapper = mock(ObjectMapper.class);
        testHelperList = new Helper[2];
        testHelperList[0] = new Helper("Steve Irwin");
        testHelperList[1] = new Helper("Uncle Ben");

        when(mockObjectMapper.readValue(new File("test_file.txt"), Helper[].class)).thenReturn(testHelperList);
		helperFileDAO = new HelperFileDAO("test_file.txt", mockObjectMapper);
    }

    @Test
    void testRemoveNeedFrommBasket() throws IOException{

    }

    @Test
    void testAddNeedToBasket() throws IOException{

    }

    @Test
    void testGetBasket() throws IOException{
        
    }
}
