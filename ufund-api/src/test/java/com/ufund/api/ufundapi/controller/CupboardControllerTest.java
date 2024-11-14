package com.ufund.api.ufundapi.controller;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

@Tag("Controller-Tier")
public class CupboardControllerTest {
    @Test
    void testGetNeeds() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        Need[] needArray = {mockNeed};
        when(mockDAO.getNeeds()).thenReturn(needArray);
        ResponseEntity<Need[]> expected = new ResponseEntity<>(needArray,HttpStatus.OK);

        assertEquals(expected,controller.getNeeds());
    }

    @Test
    void testGetNeedsException() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeeds()).thenThrow(new IOException());

        ResponseEntity<Need[]> response = controller.getNeeds();

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR,response.getStatusCode());
    }

    @Test
    void testCreateNeedSuccess() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.searchNeeds(mockNeed.getTitle())).thenReturn(new Need[0]);
        Need newMockNeed = mock(Need.class);
        when(mockDAO.createNeed(mockNeed)).thenReturn(newMockNeed);
        ResponseEntity<Need> expected = new ResponseEntity<>(newMockNeed,HttpStatus.CREATED);

        assertEquals(expected, controller.createNeed(mockNeed));
    }

    @Test
    void testCreateNeedConflict() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.searchNeeds(mockNeed.getTitle())).thenReturn(new Need[1]);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.CONFLICT);

        assertEquals(expected, controller.createNeed(mockNeed));
    }

    @Test
    void testCreateNeedsException() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.searchNeeds(mockNeed.getTitle())).thenThrow(new IOException());
        Need newMockNeed = mock(Need.class);
        when(mockDAO.createNeed(mockNeed)).thenThrow(new IOException());
        ResponseEntity<Need> response = controller.createNeed(newMockNeed);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testDeleteNeedSucess() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.deleteNeed(0)).thenReturn(true);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.OK);

        assertEquals(expected, controller.deleteNeed(0));
    }

    @Test
    void testDeleteNeedNotFound() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.deleteNeed(0)).thenReturn(false);
        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        assertEquals(expected, controller.deleteNeed(0));
    }

    @Test
    void testDeleteNeedException() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.deleteNeed(0)).thenThrow(new IOException());
        ResponseEntity<Need> response = controller.deleteNeed(0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testGetNeedSuccess() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeed(0)).thenReturn(mockNeed);
        ResponseEntity<Need> expected = new ResponseEntity<Need>(mockNeed,HttpStatus.OK);

        assertEquals(expected, controller.getNeed(0));
    }

    @Test
    void testGetNeedNotFound() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeed(0)).thenReturn(null);
        ResponseEntity<Object> expected = new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

        assertEquals(expected, controller.getNeed(0));
    }

    @Test
    void testGetNeedException() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeed(0)).thenThrow(new IOException());
        ResponseEntity<Need> response = controller.getNeed(0);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testSearchNeedsExists() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed = mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeed(0)).thenReturn(mockNeed);

        ResponseEntity<Object> expected = new ResponseEntity<>(mockNeed,HttpStatus.OK);

        assertEquals(expected, controller.getNeed(0));
    }

    @Test
    void testSearchNeedsEmpty() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need[] List = new Need[10];
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.searchNeeds(null)).thenReturn(List);

        ResponseEntity<Object> expected = new ResponseEntity<>(List,HttpStatus.OK);

        assertEquals(expected, controller.searchNeeds(null));
    }

    @Test
    void testSearchNeedsException() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.getNeed(0)).thenThrow(new IOException());

        ResponseEntity<Need> response = controller.getNeed(0);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void testUpdateNeedSuccess() throws IOException{
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed =  mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.updateNeed(mockNeed)).thenReturn(mockNeed);

        ResponseEntity<Object> expected = new ResponseEntity<>(mockNeed,HttpStatus.OK);

        assertEquals(expected, controller.updateNeed(mockNeed));
    }

    @Test
    void testUpdateNeedNotFound() throws IOException {
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed =  mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.updateNeed(mockNeed)).thenReturn(null);

        ResponseEntity<Object> expected = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        assertEquals(expected, controller.updateNeed(mockNeed));

    }

    @Test
    void testUpdateNeedException() throws IOException{
        CupboardDAO mockDAO = mock(CupboardDAO.class);
        Need mockNeed =  mock(Need.class);
        CupboardController controller = new CupboardController(mockDAO);

        when(mockDAO.updateNeed(mockNeed)).thenThrow(new IOException());

        ResponseEntity<Need> response = controller.updateNeed(mockNeed);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
