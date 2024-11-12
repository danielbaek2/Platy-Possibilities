package com.ufund.api.ufundapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.ufund.api.ufundapi.persistence.AdminDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Tag("Model-Tier")
public class MessageBoardTest {

    @Mock
    private AdminDAO adminDAO;

    @InjectMocks
    private MessageBoard messageBoard;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetMessageBoard() throws IOException {
        List<String> testBoard = Arrays.asList("Message1", "Message2");
        when(adminDAO.getMessageBoard()).thenReturn(testBoard);

        List<String> result = messageBoard.getMessageBoard();
        assertEquals(testBoard, result);
        verify(adminDAO, times(1)).getMessageBoard();
    }

    @Test
    void testAddMessage() throws IOException {
        String testMessage = "New Message";
        when(adminDAO.getMessage(testMessage)).thenReturn(true);

        boolean result = messageBoard.addMessage(testMessage);
        assertTrue(result);
        verify(adminDAO, times(1)).getMessage(testMessage);
    }

    @Test
    void testDeleteMessage() throws IOException {
        String testMessage = "Message to Delete";
        when(adminDAO.deleteMessage(testMessage)).thenReturn(true);

        boolean result = messageBoard.deleteMessage(testMessage);
        assertTrue(result);
        verify(adminDAO, times(1)).deleteMessage(testMessage);
    }
}
