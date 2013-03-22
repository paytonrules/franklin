package httpserver.responders;

import httpserver.Utilities;

import java.util.Map;

public class RedirectResponder implements Responder {
    private String redirectUri;

    public RedirectResponder(String redirectUri) {
        this.redirectUri = redirectUri;
    }

    public Map<String, Object> respond(Map<String, Object> request) {
        Map<String, String> header = Utilities.getCommonHeader("text/html", 0);
        header.put("Location", String.format("http://%s%s", request.get("Host"), redirectUri));

        return Utilities.generateResponse(Utilities.statusLine(301), header, new byte[0]);
    }
}
