package com.ufund.api.ufundapi.persistence;

import java.io.IOException;
import java.util.List;

public interface AdminDAO {
    public List<String> getMessageBoard() throws IOException;

    public boolean getMessage(String message) throws IOException;

    public boolean deleteMessage(String message) throws IOException;
}
