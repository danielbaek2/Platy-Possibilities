package com.ufund.api.ufundapi.model;

import java.util.ArrayList;
import java.util.List;

public class Helper extends User {

    private List<Need> fundingBasket;

    public Helper(String username){
        super(username);
        this.fundingBasket = new ArrayList<>();
    }

}
