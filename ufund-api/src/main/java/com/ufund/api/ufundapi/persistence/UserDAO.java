package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

import com.ufund.api.ufundapi.model.Helper;
import com.ufund.api.ufundapi.model.Need;

public interface UserDAO {
    // public boolean removeNeedFromBasket(Need need, String username) throws IOException;

    // public boolean addNeedToBasket(Need need, String username) throws IOException;
    
    // public List<Need> getBasket(String username) throws IOException;

    public boolean verifyUser(String username);

    public boolean isAdmin(String username);

    public List<Helper> userSearch(String username) throws IOException;
}

