package httpserver;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Responder> routes;

    public Router() {
        routes = new HashMap<>();
    }

    public void addRoute(String path, Responder responder) {
        routes.put(path, responder);
    }

    public Map<String, Object> route(Map<String, Object> request) throws IOException {
        Map<String, Object> response;

        Responder responder = routes.get(request.get("Request-URI"));

        if (responder != null) {
            response = responder.respond(request);
        }
        else {
            response = routes.get("filesystem").respond(request);
        }

        return response;
    }
}
