package tests;

import httpserver.Server;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public class ServerTest {
    private int port = 5000;
    private String rootDir = "/Users/nkw/git/cob_spec/public/";
    private Server server;

    @Before
    public void setUp() throws IOException {
        server = new Server(port, rootDir);
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
