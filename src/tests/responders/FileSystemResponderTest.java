package tests.responders;

import httpserver.responders.FileSystemResponder;
import httpserver.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileSystemResponderTest {
    private Map<String, Object> request;
    private File rootDir;
    private FileSystemResponder fileSystemResponder;

    @Before
    public void setUp() {
        request = new HashMap<>();
        rootDir = new File(System.getProperty("user.dir"), "public");
        fileSystemResponder = new FileSystemResponder(rootDir);

        request.put("HTTP-Version", "HTTP/1.1");
    }

    @Test
    public void testUriIsFile() throws IOException {
        File file = new File(rootDir, "test.jpg");
        assertTrue(file.isFile());

        request.put("Request-URI", "/test.jpg");

        Map<String, Object> response = fileSystemResponder.respond(request);
        Map<String, String> headers = (Map<String, String>) response.get("message-header");

        assertEquals("image/jpeg", headers.get("Content-Type"));
        assertEquals(String.valueOf(file.length()), headers.get("Content-Length"));
        assertEquals((int) file.length(), ((byte[]) response.get("message-body")).length);
    }

    @Test
    public void testUriIsDirectory() throws IOException {
        assertTrue(rootDir.isDirectory());

        byte[] bytes = Utilities.readDirAndGenerateHtml(rootDir, rootDir);

        request.put("Request-URI", "/");

        Map<String, Object> response = fileSystemResponder.respond(request);
        Map<String, String> headers = (Map<String, String>) response.get("message-header");

        assertEquals("text/html", headers.get("Content-Type"));
        assertEquals(String.valueOf(bytes.length), headers.get("Content-Length"));
        assertTrue(Arrays.equals(bytes, (byte[]) response.get("message-body")));
    }

    @Test
    public void testNotFound() throws IOException {
        request.put("Request-URI", "/should_not_exist.saj");

        File file = new File(rootDir, "404.html");

        Map<String, Object> response = fileSystemResponder.respond(request);
        Map<String, String> headers = (Map<String, String>) response.get("message-header");

        assertEquals("HTTP/1.1 404 Not Found", response.get("status-line"));
        assertEquals("text/html", headers.get("Content-Type"));
        assertEquals(String.valueOf(file.length()), headers.get("Content-Length"));
        assertEquals((int) file.length(), ((byte[]) response.get("message-body")).length);
    }
}
