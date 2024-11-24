package com.ufund.api.ufundapi.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class NeedTest {
    int ID = 1;
    String Title = "Saving Ducks";
    String Description = null;
    int Quantity = 500;
    int Cost = 25;

    Need saveDucks = new Need(ID,Title,Description,Quantity,Cost);

    @Test
    void testNeedCreation(){
        int expectedID = 1;
        String expectedTitle = "Saving Ducks";
        String expectedDescription = null;
        int expectedQuantity = 500;
        int expectedCost = 25;
        int expectedQuantityFunded = 0;

        Need NaveDucks = new Need(expectedID, expectedTitle,expectedDescription,expectedQuantity,expectedCost);

        assertEquals(expectedID, NaveDucks.getId());
        assertEquals(expectedTitle, NaveDucks.getTitle());
        assertEquals(expectedDescription, NaveDucks.getDescription());
        assertEquals(expectedQuantity, NaveDucks.getQuantity());
        assertEquals(expectedCost, NaveDucks.getCost());
        assertEquals(expectedQuantityFunded, NaveDucks.getQuantityFunded());
    }

    @Test
    void testSetDescription(){
        String expectedDescription = "Save ducks from an oilspill";

        saveDucks.setDescription(expectedDescription);

        assertEquals(expectedDescription, saveDucks.getDescription());
    }

    @Test
    void testFundNeed(){
        int expectedQuantityFunded = 5;

        saveDucks.fundNeed();
        saveDucks.fundNeed(4);

        assertEquals(expectedQuantityFunded, saveDucks.getQuantityFunded());
    }

    @Test
    void testgetAmountFunded(){

        int expectedAmountFunded = 125;

        saveDucks.fundNeed(5);

        assertEquals(expectedAmountFunded,saveDucks.amountFunded());
    }

    @Test
    void testStringFormat(){
        String expectedString = "Need [id = 1, title = Saving Ducks]";

        assertEquals(expectedString, saveDucks.toString());
    }

    @Test
    void testEquals(){
        Need DuckSaving = new Need(saveDucks.getId(), saveDucks.getTitle(), saveDucks.getDescription(), saveDucks.getQuantity(), saveDucks.getCost());

        assertEquals(DuckSaving, saveDucks);
    }

    @Test
    void testNotEquals(){
        Need DuckSaving = new Need(21, saveDucks.getTitle(), saveDucks.getDescription(), saveDucks.getQuantity(), saveDucks.getCost());

        assertNotEquals(DuckSaving, saveDucks);
    }

    @Test
    void testNotEqualsObject(){
        assertNotEquals("Test", saveDucks);
    }

    @Test
    void testNotFundable(){
        saveDucks.fundNeed(500);
        assertFalse(saveDucks.fundable());
    }

    @Test
    void testUpdateTitle(){
        String expectedTitle = "Not Saving Ducks";

        saveDucks.setTitle(expectedTitle);

        assertEquals(expectedTitle, saveDucks.getTitle());
    }

    @Test
    void testOverfundAmount(){
        saveDucks.fundNeed(600);
        assertEquals(500, saveDucks.getQuantityFunded());
    }
    
    @Test
    void testOverfundIncrement(){
        saveDucks.fundNeed(500);
        saveDucks.fundNeed();
        assertEquals(500, saveDucks.getQuantityFunded());
    }
    
}
