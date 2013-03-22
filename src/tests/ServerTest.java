package tests;

import httpserver.Router;
import httpserver.Server;
import httpserver.sockets.HttpServerSocket;
import org.junit.Before;
import org.junit.Test;
import tests.mocks.Mocket;
import tests.mocks.ServerMocket;
import tests.mocks.ServerMocketWrapper;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ServerTest {
    private Server server;
    private Router router;

    @Before
    public void setUp() throws IOException {
        router = new Router();

        HttpServerSocket serverSocket = new ServerMocketWrapper(new ServerMocket(new Mocket(null, null)));
        server = new Server(serverSocket, router);
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
