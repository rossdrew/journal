package com.rox.journal.Task;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rox.journal.task.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testSerialisation() throws JsonProcessingException {
        final Task testTask = new Task("TEST");
        final ObjectMapper objectMapper = new ObjectMapper();

        final String json = objectMapper.writeValueAsString(testTask);
        final Task deserializedTask = objectMapper.readValue(json, Task.class);

        //No exception thrown
        assertNotNull(json);
        assertNotEquals("", json);
        assertEquals(testTask, deserializedTask);
    }
}
