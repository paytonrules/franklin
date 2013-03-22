package tests;

import httpserver.Utilities;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilitiesTest {
    private Map<String, Object> response;
    private Map<String, String> headers;
    private File rootDir;

    @Before
    public void setUp() {
        response = new HashMap<>();
        headers = new HashMap<>();
        rootDir = new File(System.getProperty("user.dir"), "public");
    }

    @Test
    public void testStatusLine() {
        response.put("status-line", Utilities.statusLine(200));
        assertEquals("HTTP/1.1 200 OK", response.get("status-line"));
    }

    @Test
    public void testResponse() {
        byte[] body = "and Junk".getBytes(Charset.forName("utf-8"));
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> response = Utilities.generateResponse("Stuff", headers, body);

        assertEquals("Stuff", response.get("status-line"));
        assertEquals(headers, response.get("message-header"));
        assertEquals(body, response.get("message-body"));
    }

    @Test
    public void testCommonHeaders() {
        headers = Utilities.getCommonHeader("text/html", 352);
        assertEquals("text/html", headers.get("Content-Type"));
        assertEquals("352", headers.get("Content-Length"));
        assertEquals("close", headers.get("Connection"));
    }

    @Test
    public void testDirectoryDoesNotExist() throws IOException {
        File file = new File("/stuff_and_junk/");
        byte[] bytes = Utilities.readDirAndGenerateHtml(file, rootDir);
        assertEquals(0, bytes.length);
    }

    @Test
    public void testDirectoryExists() throws IOException {
        File file = new File(System.getProperty("user.dir"), "public");
        byte[] bytes = Utilities.readDirAndGenerateHtml(file, rootDir);
        assertTrue(bytes.length > 0);
    }

    @Test
    public void testFileDoesNotExist() throws IOException {
        File file = new File(rootDir, "fake_file");
        byte[] fileBytes = Utilities.readFile(file);
        assertEquals(0, fileBytes.length);
    }

    @Test
    public void testFileDoesExist() throws IOException {
        File file = new File(rootDir + "/test.jpg");
        byte[] fileBytes = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        for (int i = 0; i < fileBytes.length; i++) {
            fileBytes[i] = (byte) inputStream.read();
        }
        inputStream.close();

        assertTrue(Arrays.equals(fileBytes, Utilities.readFile(file)));
    }
}
