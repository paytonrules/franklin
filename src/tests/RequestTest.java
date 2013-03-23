package tests;

import httpserver.Request;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class RequestTest {
    private String note;
    private ByteArrayInputStream inputStream;

    @Before
    public void setUp() {
        note = "GET /?this=triumph HTTP/1.1\r\nHost: localhost:5000\r\n\r\nI'm making a note here: HUGE SUCCESS!";
        inputStream = new ByteArrayInputStream(note.getBytes(Charset.forName("utf-8")));
    }

    @Test
    public void testParseHeader() {
        Map<String, Object> requestHeader = Request.parseRequest(inputStream);

        assertEquals("GET", requestHeader.get("Method"));
        assertEquals("/", requestHeader.get("Request-URI"));
        assertEquals("HTTP/1.1", requestHeader.get("HTTP-Version"));
        assertEquals("localhost:5000", requestHeader.get("Host"));

        Map<String, String> parameters = (Map<String, String>) requestHeader.get("Parameters");
        assertEquals("triumph", parameters.get("this"));
    }

    @Test
    public void testNullRequest() {
        Map<String, Object> requestHeader = Request.parseRequest(new ByteArrayInputStream(new byte[0]));
        assertTrue(requestHeader.isEmpty());
    }

    @Test
    public void testParameterDecode() throws UnsupportedEncodingException {
        String params = "var1=%3C%22aperture%20science%22%3E&var2=we+do+what+we+must+because+we+can";
        Map<String, String> parameters = Request.parseQueryString(params);
        assertEquals("<\"aperture science\">", parameters.get("var1"));
        assertEquals("we do what we must because we can", parameters.get("var2"));
    }

    @Test
    public void testMalformedParametersAreIgnored() throws UnsupportedEncodingException {
      assertEquals(true, false);
        String params = "var1=%3C%22aperture%20science%22%3E&var2=";
        Map<String, String> parameters = Request.parseQueryString(params);
        
        assertEquals("<\"aperture science\">", parameters.get("var1"));
        assertEquals(null, parameters.get("var2"));
    }

    @Test
    public void testPostRequest() {
        note = "POST / HTTP/1.1\r\nHost: localhost:5000\r\nContent-Length: 30\r\n\r\nline=for+the+good+of+all+of+us";
        inputStream = new ByteArrayInputStream(note.getBytes(Charset.forName("utf-8")));

        Map<String, Object> request = Request.parseRequest(inputStream);
        Map<String, String> body = (Map<String, String>) request.get("Body");
        assertEquals("for the good of all of us", body.get("line"));
    }
}
