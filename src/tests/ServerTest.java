package tests;

import httpserver.Router;
import httpserver.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;


public class ServerTest {
    private int port = 5000;
    private Server server;
    private Router router;

    @Before
    public void setUp() throws IOException {
        router = new Router();
        server = new Server(port, router);
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
