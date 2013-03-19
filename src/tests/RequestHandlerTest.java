package tests;

import httpserver.RequestHandler;
import httpserver.Responder;
import httpserver.Router;
import httpserver.sockets.HttpSocket;
import org.junit.Before;
import org.junit.Test;
import tests.mocks.Mocket;
import tests.mocks.MocketWrapper;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RequestHandlerTest {
    private HttpSocket socket;
    private Router router;
    private InputStream inputStream;
    private OutputStream outputStream;
    private RequestHandler requestHandler;

    @Before
    public void setUp() {
        router = new Router();
        String request = "HTTP/1.1 / GET\r\n\r\n";
        inputStream = new ByteArrayInputStream(request.getBytes(Charset.forName("utf-8")));
        outputStream = new ByteArrayOutputStream();
        socket = new MocketWrapper(new Mocket(inputStream, outputStream));
        requestHandler = new RequestHandler(socket, router);
    }

    @Test
    public void testRun() {
        router.addRoute("/", new Responder() {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                Map<String, Object> response = new HashMap<>();
                response.put("message-header", new HashMap<String, String>());
                response.put("status-line", "HTTP/1.1 200 OK");
                response.put("message-body", "We do what we must because we can.".getBytes(Charset.forName("utf-8")));
                return response;
            }
        });

        requestHandler.run();
        assertEquals("HTTP/1.1 200 OK\r\n\r\nWe do what we must because we can.", outputStream.toString());
    }
}
