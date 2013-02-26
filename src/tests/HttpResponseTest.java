package tests;

import httpserver.HttpResponse;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;

public class HttpResponseTest {
    @Test
    public void testWrite() throws IOException {
        String triumph = "This was a triumph.";
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        HttpResponse.write(triumph, outputStream);
        assertEquals(triumph, outputStream.toString());
    }
}
