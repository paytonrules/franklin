package tests.responders;

import httpserver.responders.ParameterResponder;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ParameterResponderTest {

    @Test
    public void testResponder() throws IOException {
        Map<String, Object> request = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        parameters.put("string", "except the ones who are dead");

        request.put("HTTP-Version", "HTTP/1.1");
        request.put("Parameters", parameters);

        ParameterResponder responder = new ParameterResponder();
        Map<String, Object> response = responder.respond(request);
        String body = new String((byte[]) response.get("message-body"));
        assertTrue(body.contains("string = except the ones who are dead"));
    }
}
