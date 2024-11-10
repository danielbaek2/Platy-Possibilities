package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Need;

public interface HelperDAO {
    public boolean removeNeedFromBasket(Need need, String username) throws IOException;

    public boolean addNeedToBasket(Need need, String username) throws IOException;
    
    public List<Need> getBasket(String username) throws IOException;

    public boolean addMessage(String message, String username) throws IOException; 
}

