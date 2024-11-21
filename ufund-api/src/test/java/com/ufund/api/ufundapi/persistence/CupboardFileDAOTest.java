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
import com.ufund.api.ufundapi.model.Need;

/**
 * Test the Cupboard File Data Access Object class
 */
@Tag("Persistence-Tier")
public class CupboardFileDAOTest{
	CupboardFileDAO cupboardFileDAO;
	Need[] testNeeds;
	ObjectMapper mockObjectMapper;

	@BeforeEach
	void setUpCupboardFileDAO() throws IOException {
		mockObjectMapper = mock(ObjectMapper.class);
		testNeeds = new Need[2];
		testNeeds[0] = new Need(4, "Shelter Dogs Walk",null,5,50);
		testNeeds[1] = new Need(5, "Elephant Aid",null,5,50);

		when(mockObjectMapper.readValue(new File("test_file.txt"), Need[].class)).thenReturn(testNeeds);
		cupboardFileDAO = new CupboardFileDAO("test_file.txt", mockObjectMapper);
	}

	@Test
	void testGetNeed() throws IOException{
		// Setup
		int id = 4;

		// Invoke
		Need actual = cupboardFileDAO.getNeed(id);

		// Analyze
        assertEquals(testNeeds[0], actual);
    }

	@Test
	void testGetNeedNotFound() throws IOException{
		// Setup
		int id = 1;
		Need expected = null;

		// invoke
		Need actual = assertDoesNotThrow(() -> cupboardFileDAO.getNeed(id), "Unexpected exception thrown");

		// Analyze
        assertEquals(expected, actual);
    }

	@Test
	void testGetNeeds() throws IOException {
		// Invoke
		Need[] actual = cupboardFileDAO.getNeeds();

		// Analyze
        assertEquals(testNeeds.length, actual.length);
		for (int i = 0; i < testNeeds.length; ++i){
			assertEquals(testNeeds[i], actual[i]);
		}
    }

	@Test
	void testSearchNeeds() throws IOException{
		// invoke
		Need[] actual = cupboardFileDAO.searchNeeds("er");
		
		// analyze
        assertEquals(testNeeds[0], actual[0]);
    }

	@Test
	void testDeleteNeed() throws IOException{
		// Setup
        int id = 5;
		boolean expected = true;

		// invoke
		boolean actual = cupboardFileDAO.deleteNeed(id);
		// analyze
        assertEquals(expected, actual);
    }

	@Test
	void testCreateNeed() throws IOException{
		// Setup
		Need expectedNeed = new Need(4, "Shelter Dogs Walk",null,5,50);
		// Invoke
		Need result = assertDoesNotThrow(() -> cupboardFileDAO.createNeed(expectedNeed), "Unexpected exception thrown");

		// Analyze
		assertNotNull(result);
		Need actual = cupboardFileDAO.getNeed(expectedNeed.getId());
        assertEquals(expectedNeed.getId(), actual.getId());
        assertEquals(expectedNeed.getTitle(), actual.getTitle());
    }

	@Test
	void testDeleteNeedNotFound() throws IOException{
		// Setup
        int id = 1;
		boolean expected = false;

		// invoke
		boolean actual = assertDoesNotThrow(() -> cupboardFileDAO.deleteNeed(id), "Unexpected exception thrown");

		// Analyze
        assertEquals(expected, actual);
    }

	@Test
	void testConstructorException() throws IOException{
		// Setup
		mockObjectMapper = mock(ObjectMapper.class);
		doThrow(new IOException()).when(mockObjectMapper).readValue(new File("test_file.txt"), Need[].class);

		// Invoke
		try{
			new CupboardFileDAO("test_file.txt", mockObjectMapper);
		// Analyze
			fail("IOException not thrown");
		}
		catch (IOException ioe){}
	}
	
	@Test
	void testUpdateNeed() throws IOException{
		// Setup
		int id = 4;
		Need actual = cupboardFileDAO.getNeed(id);
		
		// Invoke
		Need updatedNeed = cupboardFileDAO.updateNeed(actual);

		// Analyze
		assertEquals(updatedNeed, actual);
	}

	@Test
	void testUpdateNeedNotFound() throws IOException{
		// Setup
		int id = 8;
		Need result = cupboardFileDAO.getNeed(id);

		// Analyze
		assertNull(result);
	}
}
