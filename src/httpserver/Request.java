package httpserver;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Request {
    public static Map<String, Object> parseRequest(InputStream inputStream) {
        Map<String, Object> requestHeader = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = reader.readLine();
            String[] items;

            items = line.split("\\s");
            requestHeader.put("Method", items[0]);
            requestHeader.put("HTTP-Version", items[2]);

            items = items[1].split("\\?");
            requestHeader.put("Request-URI", URLDecoder.decode(items[0], "utf-8"));
            if (items.length > 1) {
                requestHeader.put("Parameters", parseQueryString(items[1]));
            }

            requestHeader.putAll(parseMessageHeaders(reader));

            int length = 0;
            try {
                length = Integer.parseInt((String) requestHeader.get("Content-Length"));
            }
            catch (NumberFormatException e) {}
            requestHeader.put("Body", parseBody(reader, length));
        }
        catch (Exception e) {
            requestHeader = new HashMap<>();
        }

        return requestHeader;
    }

    public static Map<String, String> parseQueryString(String parameters) throws UnsupportedEncodingException {
        Map<String, String> decodedQueryString = new HashMap<>();

        for(String parameter: parameters.split("&")) {
            String[] splitParameter = parameter.split("=");
            if (splitParameter.length == 2) {
                decodedQueryString.put(splitParameter[0], URLDecoder.decode(splitParameter[1], "utf-8"));
            }
        }

        return decodedQueryString;
    }

    private static Map<String, Object> parseMessageHeaders(BufferedReader reader) throws IOException {
        Map<String, Object> headers = new HashMap<>();
        String[] items;
        String line;

        while(!(line = reader.readLine()).equals("")) {
            items = line.split(": ");
            headers.put(items[0], items[1]);
        }

        return headers;
    }

    private static Map<String, String> parseBody(BufferedReader reader, int length) throws IOException {
        char[] query = new char[length];
        reader.read(query);

        return parseQueryString(String.valueOf(query));
    }
}
