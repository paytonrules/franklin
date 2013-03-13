package tests;

import httpserver.RequestReader;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RequestReaderTest {
    private String note;
    private ByteArrayInputStream inputStream;

    @Before
    public void setUp() {
        note = "GET /?this=triumph HTTP/1.1\r\nHost: localhost:5000\r\n\r\nI'm making a note here: HUGE SUCCESS!";
        inputStream = new ByteArrayInputStream(note.getBytes(Charset.forName("utf-8")));
    }

    @Test
    public void testParseHeader() throws IOException {

        Map<String, Object> requestHeader = RequestReader.parseHeader(inputStream);

        assertEquals("GET", requestHeader.get("Method"));
        assertEquals("/", requestHeader.get("Request-URI"));
        assertEquals("HTTP/1.1", requestHeader.get("HTTP-Version"));
        assertEquals("localhost:5000", requestHeader.get("Host"));

        Map<String, String> parameters = (Map<String, String>) requestHeader.get("Parameters");
        assertEquals("triumph", parameters.get("this"));
    }


    @Test
    public void testParameterDecode() throws IOException {
        String params = "var1=%3C%22aperture%20science%22%3E&var2=we+do+what+we+must+because+we+can";
        Map<String, String> parameters = RequestReader.decodeParameters(params);
        assertEquals("<\"aperture science\">", parameters.get("var1"));
        assertEquals("we do what we must because we can", parameters.get("var2"));
    }
}
