package tests;

import httpserver.HttpRequest;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import static junit.framework.Assert.assertEquals;

public class HttpRequestTest {
    @Test
    public void testParse() throws IOException {
        String note = "I'm making a note here: HUGE SUCCESS!";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(note.getBytes(Charset.forName("utf-8")));
        assertEquals(note, HttpRequest.parse(inputStream));
    }
}
