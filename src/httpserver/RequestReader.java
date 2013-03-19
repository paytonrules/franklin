package httpserver;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class RequestReader {
    public static Map<String, Object> parseHeader(InputStream inputStream) {
        Map<String, Object> requestHeader = new HashMap<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = reader.readLine();
            String[] items;

            items = line.split("\\s");
            requestHeader.put("Method", items[0]);
            requestHeader.put("HTTP-Version", items[2]);

            items = items[1].split("\\?");
            requestHeader.put("Request-URI", items[0]);
            if (items.length > 1) {
                requestHeader.put("Parameters", decodeParameters(items[1]));
            }

            while (!(line = reader.readLine()).equals("")) {
                items = line.split(": ");
                requestHeader.put(items[0], items[1]);
            }
        }
        catch (Exception e) {
            requestHeader = new HashMap<>();
        }

        return requestHeader;
    }

    public static Map<String, String> decodeParameters(String parameters) throws UnsupportedEncodingException {
        Map<String, String> decodedParameters = new HashMap<>();

        for(String parameter: parameters.split("&")) {
            String[] splitParameter = parameter.split("=");
            if (splitParameter.length == 2) {
                decodedParameters.put(splitParameter[0], URLDecoder.decode(splitParameter[1], "utf-8"));
            }
        }

        return decodedParameters;
    }
}
