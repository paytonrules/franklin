package httpserver;

import java.util.HashMap;
import java.util.Map;

public class RedirectResponder implements Responder {
    private String redirectUri;

    public RedirectResponder(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Map<String, Object> respond(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

        ResponseCode.threeOhOne(response);
        headers.put("Location", String.format("http://%s%s", request.get("Host"), redirectUri));
        headers.put("Content-Length", "0");
        response.put("message-header", headers);
        response.put("message-body", new byte[0]);

        return response;
    }
}
