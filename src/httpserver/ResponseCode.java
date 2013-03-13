package httpserver;

import java.util.Map;

public class ResponseCode {
    public static void twoHundred(Map<String, Object> response) {
        response.put("status-line", statusLine(200, "OK"));
    }

    public static void fourOhFour(Map<String, Object> response) {
        response.put("status-line", statusLine(404, "Not Found"));
    }

    public static void threeOhOne(Map<String, Object> response) {
        response.put("status-line", statusLine(301, "Moved Permanently"));
    }

    private static String statusLine(int code, String phrase) {
        return String.format("HTTP/1.1 %d %s", code, phrase);
    }
}
