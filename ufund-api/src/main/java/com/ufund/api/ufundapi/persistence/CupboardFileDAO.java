package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.ufund.api.ufundapi.model.Need;

public class CupboardFileDAO implements CupboardDAO{
    
    @Override
    public Need CreateNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Need UpdateNeed(Need need) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public Need GetNeed(int id) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Need[] GetNeeds() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Need[] SearchNeeds(String substring) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean DeleteNeed(int id) throws IOException {
        // TODO Auto-generated method stub
        return false;
    }
}
