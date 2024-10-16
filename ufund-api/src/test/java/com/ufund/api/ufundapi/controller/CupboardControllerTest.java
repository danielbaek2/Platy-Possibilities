package com.ufund.api.ufundapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.io.IOException;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.persistence.CupboardDAO;

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
    void testSearchNeedsExists() {

    }

    @Test
    void testSearchNeedsEmpty() {

    }

    @Test
    void testUpdateNeedSuccess() {

    }

    @Test
    void testUpdateNeedNotFound() {

    }
}
