package httpserver;

import httpserver.responders.Responder;

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
        Responder responder = routes.get(request.get("Request-URI"));

        if (responder != null) {
            return responder.respond(request);
        }
        else {
            return routes.get("filesystem").respond(request);
        }
    }
}
