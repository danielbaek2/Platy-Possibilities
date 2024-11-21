package com.ufund.api.ufundapi.persistence;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestFileDao extends UserFileDAO {

    public TestFileDao(String filename, ObjectMapper mapper) throws IOException{
        super(filename,mapper);
    }
}
