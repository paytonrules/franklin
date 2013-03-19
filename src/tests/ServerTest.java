package tests;

import httpserver.Responder;
import httpserver.Router;
import httpserver.Server;
import httpserver.sockets.HttpServerSocket;
import org.junit.Before;
import org.junit.Test;
import tests.mocks.Mocket;
import tests.mocks.ServerMocket;
import tests.mocks.ServerMocketWrapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ServerTest {
    private Server server;
    private Router router;
    private ByteArrayInputStream inputStream;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() throws IOException {
        router = new Router();

        String request = "HTTP/1.1 / GET\r\n\r\n";
        inputStream = new ByteArrayInputStream(request.getBytes(Charset.forName("utf-8")));
        outputStream = new ByteArrayOutputStream();
        HttpServerSocket serverSocket = new ServerMocketWrapper(new ServerMocket(new Mocket(inputStream, outputStream)));

        server = new Server(serverSocket, router);
    }

    @Test
    public void testOneRequest() throws IOException {
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

        server.run();
        assertEquals("HTTP/1.1 200 OK\r\n\r\nWe do what we must because we can.", outputStream.toString());
    }

    @Test
    public void testServerCreated() throws IOException {
        assertTrue(server.isBound());
        server.close();
    }

    @Test
    public void testClose() throws IOException {
        server.close();
        assertTrue(server.isClosed());
    }
}
