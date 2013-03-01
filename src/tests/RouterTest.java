package tests;

import httpserver.Router;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class RouterTest {
    private Router router;
    private Map<String, Object> request = new HashMap<>();

    @Before
    public void setUp() {
        router = new Router();
        request.put("RequestReader-URI", "/");
        request.put("HTTP-Version", "HTTP/1.1");
    }

    @Test
    public void testRouteNotInRoutes() {
        Map<String, Object> response = router.route(request);
        assertEquals("HTTP/1.1", response.get("HTTP-Version"));
        assertEquals("404", response.get("Status-Code"));
        assertEquals("Not Found", response.get("Reason-Phrase"));
    }

    @Test
    public void testRouteInRoutes() {
        router.addRoute("/");

        Map<String, Object> response = router.route(request);
        assertEquals("HTTP/1.1", response.get("HTTP-Version"));
        assertEquals("200", response.get("Status-Code"));
        assertEquals("OK", response.get("Reason-Phrase"));
    }
}
