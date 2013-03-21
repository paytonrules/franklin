package httpserver.responders;

import httpserver.Utilities;

import java.util.HashMap;
import java.util.Map;

public class RedirectResponder implements Responder {
    private String redirectUri;

    public RedirectResponder(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Map<String, Object> respond(Map<String, Object> request) {
        Map<String, String> headers = new HashMap<>();

        byte[] body = new byte[0];

        Utilities.writeCommonHeaders(headers, "text/html", body.length);
        headers.put("Location", String.format("http://%s%s", request.get("Host"), redirectUri));

        return Utilities.generateResponse(Utilities.statusLine(301), headers, body);
    }
}
