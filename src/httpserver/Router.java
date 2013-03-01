package httpserver;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Object> routes;

    public Router() {
        routes = new HashMap<>();
    }

    public void addRoute(String path) {
        routes.put(path, new Object());
    }

    public Map<String, Object> route(Map<String, Object> request) {
        Map<String, Object> response = new HashMap<>();

        response.put("HTTP-Version", request.get("HTTP-Version"));

        if (routes.get(request.get("RequestReader-URI")) != null) {
            ResponseCode.twoHundred(response);
        }
        else {
            ResponseCode.fourOhFour(response);
        }

        return response;
    }
}
