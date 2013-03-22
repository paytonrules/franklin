package httpserver.responders;

import httpserver.HtmlGenerator;
import httpserver.Utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class PutPostResponder implements Responder {
    private Map<String, String> storedBody = new HashMap<>();
    private HtmlGenerator generator;

    public PutPostResponder() {
        generator = new HtmlGenerator();
    }

    @Override
    public Map<String, Object> respond(Map<String, Object> request) throws IOException {
        byte[] body = new byte[0];

        if (request.get("Method").equals("GET")) {
            body = generator.getEchoPage(storedBody, "%s = %s<br/>").getBytes(Charset.forName("utf-8"));
        }
        else {
            if (request.get("Body") != null) {
                storedBody = (Map<String, String>) request.get("Body");
            }
        }

        Map<String, String> header = Utilities.getCommonHeader("text/html", body.length);
        return Utilities.generateResponse(Utilities.statusLine(200), header, body);
    }
}
