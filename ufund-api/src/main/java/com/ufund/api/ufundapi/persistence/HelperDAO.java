package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.Helper;

public interface HelperDAO {
    public void removeNeedFromBasket(Need need, String username) throws IOException;

    public void addNeedToBasket(Need need, String username) throws IOException;
    
    public List<Need> getBasket(String username) throws IOException;
}

