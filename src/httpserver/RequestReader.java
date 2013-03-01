package httpserver;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    public static Map<String, Object> parseHeader(InputStream inputStream) throws IOException {
        Map<String, Object> requestHeader = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = reader.readLine();
        String[] items;

        items = line.split("\\s");
        requestHeader.put("Method", items[0]);
        requestHeader.put("RequestReader-URI", items[1]);
        requestHeader.put("HTTP-Version", items[2]);

        while (!(line = reader.readLine()).equals("")) {
            items = line.split(": ");
            requestHeader.put(items[0], items[1]);
        }

        return requestHeader;
    }
}
