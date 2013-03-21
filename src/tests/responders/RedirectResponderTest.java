package tests.responders;

import httpserver.responders.RedirectResponder;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RedirectResponderTest {
    RedirectResponder responder;
    Map<String, Object> request;

    @Before
    public void setUp() {
        responder = new RedirectResponder("/fakefake");
        request = new HashMap<>();
        request.put("HTTP-Version", "HTTP/1.1");
        request.put("Host", "www.fakesite.com");
    }

    @Test
    public void testRedirect() {
        Map<String, Object> response = responder.respond(request);
        Map<String, String> headers = (Map<String, String>) response.get("message-header");

        assertEquals("HTTP/1.1 301 Moved Permanently", response.get("status-line"));
        assertEquals("http://www.fakesite.com/fakefake", headers.get("Location"));
        assertNotNull(response.get("message-body"));
    }
}
