package tests;

import httpserver.ResponseCode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ResponseCodeTest {
    private Map<String, Object> response;

    @Before
    public void setUp() {
        response = new HashMap<>();
    }

    @Test
    public void testTwoHundred() {
        ResponseCode.twoHundred(response);
        assertEquals("HTTP/1.1 200 OK", response.get("status-line"));
    }

    @Test
    public void testFourOhFour() {
        ResponseCode.fourOhFour(response);
        assertEquals("HTTP/1.1 404 Not Found", response.get("status-line"));
    }

    @Test
    public void testThreeOnOne() {
        ResponseCode.threeOhOne(response);
        assertEquals("HTTP/1.1 301 Moved Permanently", response.get("status-line"));
    }
}
