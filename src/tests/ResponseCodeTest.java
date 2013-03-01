package tests;

import httpserver.ResponseCode;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class ResponseCodeTest {
    private Map<String, Object> response;

    @Before
    public void setUp() {
        response = new HashMap<>();
    }

    @Test
    public void testTwoHundred() {
        ResponseCode.twoHundred(response);
        assertEquals("200", response.get("Status-Code"));
        assertEquals("OK", response.get("Reason-Phrase"));
    }

    @Test
    public void testFourOhFour() {
        ResponseCode.fourOhFour(response);
        assertEquals("404", response.get("Status-Code"));
        assertEquals("Not Found", response.get("Reason-Phrase"));
    }
}
