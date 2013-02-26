package tests;

import httpserver.HttpServer;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;

public class HttpServerTest {
    private int port = 5000;
    private String rootDir = "/Users/nkw/git/cob_spec/public/";
    private HttpServer httpServer;

    @Before
    public void setUp() throws IOException {
        httpServer = new HttpServer(port, rootDir);
    }

    @Test
    public void testServerCreated() throws IOException {
        assertTrue(httpServer.isBound());
        httpServer.close();
    }

    @Test
    public void testClose() throws IOException {
        httpServer.close();
        assertTrue(httpServer.isClosed());
    }
}
