package com.ufund.api.ufundapi.persistence;

public interface UserDAO {
    // public boolean removeNeedFromBasket(Need need, String username) throws IOException;

    // public boolean addNeedToBasket(Need need, String username) throws IOException;
    
    // public List<Need> getBasket(String username) throws IOException;

    public boolean verifyUser(String username);

    public boolean isAdmin(String username);
}

