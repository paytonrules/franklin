package httpserver;

import java.lang.String;

public class ResponseGenerator {
    public static String respond(String request, int responseCode, String body) {
        return "HTTP/1.1 " + responseCode + " OK\n\n" + body;
    }
}
