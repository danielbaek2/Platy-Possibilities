package com.ufund.api.ufundapi.model;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.ufund.api.ufundapi.model.Need;


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
        Need DuckSaving = new Need(saveDucks.getId(), saveDucks.getTitle());

        Boolean expectedResult = true;

        assertEquals(expectedResult, DuckSaving.equals(saveDucks));
    }

    @Test
    void updateTitle(){
        String expectedTitle = "Not Saving Ducks";

        saveDucks.setTitle(expectedTitle);

        assertEquals(expectedTitle, saveDucks.getTitle());
    }
    
}
