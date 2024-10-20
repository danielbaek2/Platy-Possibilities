package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Tag("Model-Tier")
public class UserTest {

    @Test
    void testCreateHelper() {
        String expectedUsername = "TestUsername";

        Helper helper = new Helper(expectedUsername);

        assertEquals(expectedUsername, helper.getUsername());
    }

    @Test
    void testHasBasket() {
        String username = "TestUsername";
        Helper helper = new Helper(username);
        int expectedBasketSize = 0;

        List<Need> basket = helper.getBasket();

        assertEquals(expectedBasketSize, basket.size());
    }

    @Test
    void testAddNeedToBasket() {
        String username = "TestUsername";
        Helper helper = new Helper(username);
        Need need = mock(Need.class);
        List<Need> expectedBasket = new ArrayList<>();
        expectedBasket.add(need);

        helper.addNeedToBasket(need);

        assertEquals(expectedBasket, helper.getBasket());
    }

    @Test
    void testRemoveNeed() {
        String username = "TestUsername";
        Helper helper = new Helper(username);
        Need need = mock(Need.class);
        helper.addNeedToBasket(need);
        List<Need> expectedBasket = new ArrayList<>();

        helper.removeNeedFromBasket(need);

        assertEquals(expectedBasket, helper.getBasket());
    }
}
