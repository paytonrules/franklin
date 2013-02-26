package tests;

import httpserver.ResponseGenerator;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ResponseGeneratorTest {
    @Test
    public void testTwoHundred() {
        String request = "GET / HTTP/1.1\n";
        String body = "<html><body>It's hard to overstate my satisfaction.</body></html>";
        String response = ResponseGenerator.respond(request, 200, body);
        assertEquals("HTTP/1.1 200 OK\n\n" + body, response);
    }
}
