package httpserver;

import java.io.IOException;
import java.util.Map;

public interface Responder {
    public Map<String, Object> respond(Map<String, Object> request) throws IOException;
}
