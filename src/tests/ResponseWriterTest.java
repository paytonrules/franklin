package tests;

import httpserver.ResponseWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ResponseWriterTest {
    private Map<String, Object> response = new HashMap<>();

    @Before
    public void setUp() {
        byte[] bytes = "<!DOCTYPE HTML><html><body>This was a triumph.</body></html>".getBytes(Charset.forName("utf-8"));

        Map<String, String> headers = new HashMap<>();

        response.put("status-line", "HTTP/1.1 200 OK");
        response.put("Body", bytes);
        headers.put("Content-Length", String.valueOf(bytes.length));
        headers.put("Content-Type", "text/html");
        response.put("message-header", headers);
        response.put("message-body", bytes);
    }

    @Test
    public void testWrite() throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ResponseWriter.write(response, outputStream);
        String html = outputStream.toString();

        assertTrue(html.contains("HTTP/1.1 200 OK\r\n"));
        assertTrue(html.contains("Content-Length: 60\r\n"));
        assertTrue(html.contains("Content-Type: text/html\r\n"));
        assertTrue(html.contains("\r\n\r\n"));
        assertTrue(html.contains("<!DOCTYPE HTML><html><body>This was a triumph.</body></html>"));
    }
}
