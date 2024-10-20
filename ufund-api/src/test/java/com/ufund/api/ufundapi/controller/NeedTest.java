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


public class NeedTest {
    int ID = 1;
    String Title = "Saving Ducks";
    int Quantity = 500;
    int Cost = 25;

    Need saveDucks = new Need(ID,Title,Quantity,Cost);

    @Test
    void testNeedCreation(){
        int expectedID = 1;
        String expectedTitle = "Saving Ducks";
        String expectedDescription = null;
        int expectedQuantity = 500;
        int expectedCost = 25;
        int expectedQuantityFunded = 0;

        Need NaveDucks = new Need(expectedID, expectedTitle,expectedQuantity,expectedCost);

        assertEquals(expectedID, NaveDucks.getId());
        assertEquals(expectedTitle, NaveDucks.getTitle());
        assertEquals(expectedDescription, NaveDucks.getDescription());
        assertEquals(expectedQuantity, NaveDucks.getQuantity());
        assertEquals(expectedCost, NaveDucks.getCost());
        assertEquals(expectedQuantityFunded, NaveDucks.getQuantityFunded());
    }

    @Test
    void testSetDescription(){
        int ID = 1;
        String Title = "Saving Ducks";
        int Quantity = 500;
        int Cost = 25;

        String expectedDescription = "Save ducks from an oilspill";

        Need saveDucks = new Need(ID,Title,Quantity,Cost);

        saveDucks.setDescription(expectedDescription);

        assertEquals(expectedDescription, saveDucks.getDescription());
    }

    @Test
    void testFundNeed(){
        int ID = 1;
        String Title = "Saving Ducks";
        int Quantity = 500;
        int Cost = 25;

        int expectedQuantityFunded = 5;

        Need saveDucks = new Need(ID,Title,Quantity,Cost);

        saveDucks.fundNeed();
        saveDucks.fundNeed(4);

        assertEquals(expectedQuantityFunded, saveDucks.getQuantityFunded());
    }

    @Test
    void testgetAmountFunded(){}


    
}
