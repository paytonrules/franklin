package tests;

import httpserver.RequestReader;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class RequestTest {
    @Test
    public void testParseHeader() throws IOException {
        String note = "GET / HTTP/1.1\r\nHost: localhost:5000\r\n\r\nI'm making a note here: HUGE SUCCESS!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(note.getBytes(Charset.forName("utf-8")));
        Map<String, Object> requestHeader = RequestReader.parseHeader(inputStream);
        assertEquals("GET", requestHeader.get("Method"));
        assertEquals("/", requestHeader.get("RequestReader-URI"));
        assertEquals("HTTP/1.1", requestHeader.get("HTTP-Version"));
        assertEquals("localhost:5000", requestHeader.get("Host"));
    }
}
