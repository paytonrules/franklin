package tests;

import httpserver.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class ResponseTest {
    private Map<String, Object> response = new HashMap<>();

    @Before
    public void setUp() {
        response.put("HTTP-Version", "HTTP/1.1");
        response.put("Status-Code", "200");
        response.put("Reason-Phrase", "OK");
        response.put("Body", "<html><body>This was a triumph.</body></html>");
    }

    @Test
    public void testWrite() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Response.write(response, outputStream);
        assertEquals("HTTP/1.1 200 OK\r\n\r\n<html><body>This was a triumph.</body></html>", outputStream.toString());
    }
}
