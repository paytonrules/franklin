package httpserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpRequest {
    public static String parse(InputStream inputStream) throws IOException {
        String request = "";

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        while (bufferedReader.ready()) {
            request += bufferedReader.readLine();
        }

        return request;
    }
}
