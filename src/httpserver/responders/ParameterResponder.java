package httpserver.responders;

import httpserver.HtmlGenerator;
import httpserver.Utilities;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class ParameterResponder implements Responder {
    private HtmlGenerator generator;

    public ParameterResponder() {
        generator = new HtmlGenerator();
    }

    @Override
    public Map<String, Object> respond(Map<String, Object> request) throws IOException {
        Map<String, String> parameters = (Map<String, String>) request.get("Parameters");
        byte[] body = generator.getEchoPage(parameters, "%s = %s<br/>").getBytes(Charset.forName("utf-8"));

        Map<String, String> header = Utilities.getCommonHeader("text/html", body.length);
        return Utilities.generateResponse(Utilities.statusLine(200), header, body);
    }
}
