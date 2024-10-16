package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Need;
import com.ufund.api.ufundapi.model.User;

public interface HelperDAO {
    public void removeNeedFromBasket(Need need);

    public void addNeedToBasket(Need need);
    
    public List<Need> getBasket();
}

