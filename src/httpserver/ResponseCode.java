package httpserver;

import java.util.Map;

public class ResponseCode {
    public static void twoHundred(Map<String, Object> response) {
        response.put("Status-Code", "200");
        response.put("Reason-Phrase", "OK");
    }

    public static void fourOhFour(Map<String, Object> response) {
        response.put("Status-Code", "404");
        response.put("Reason-Phrase", "Not Found");
    }
}
