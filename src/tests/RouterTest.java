package tests;

import httpserver.*;
import httpserver.responders.FileSystemResponder;
import httpserver.responders.Responder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RouterTest {
    private File rootDir = new File(System.getProperty("user.dir"), "/public");
    private Router router;
    private Map<String, Object> request = new HashMap<>();

    @Before
    public void setUp() {
        router = new Router();
        router.addRoute("filesystem", new FileSystemResponder(rootDir));

        request.put("Request-URI", "/");
        request.put("HTTP-Version", "HTTP/1.1");
    }

    @Test
    public void testRouteInRoutes() throws IOException {
        router.addRoute("/", new Responder() {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                Map<String, Object> response = new HashMap<>();
                response.put("status-line", Utilities.statusLine(200));
                return response;
            }
        });

        Map<String, Object> response = router.route(request);

        assertEquals("HTTP/1.1 200 OK", response.get("status-line"));
    }

}
