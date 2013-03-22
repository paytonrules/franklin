package httpserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Utilities {
    private static Map<Integer, String> statusCodes = createCodesMap();

    public static Map<String, String> getCommonHeader(String type, int size) {
        Map<String, String> headers = new HashMap<>();

        headers.put("Content-Type", type);
        headers.put("Content-Length", String.valueOf(size));
        headers.put("Connection", "close");
        headers.put("Server", "Franklin-0.1");

        return headers;
    }

    public static String statusLine(int code) {
        return String.format("HTTP/1.1 %d %s", code, statusCodes.get(code));
    }

    public static Map<String, Object> generateResponse(String statusLine, Map<String, String> messageHeader, byte[] messageBody) {
        Map<String, Object> response = new HashMap<>();

        response.put("status-line", statusLine);
        response.put("message-header", messageHeader);
        response.put("message-body", messageBody);

        return response;
    }

    public static byte[] readDirAndGenerateHtml(File directory, File rootDir) throws IOException {
        if (!directory.isDirectory())
            return new byte[0];
        HtmlGenerator generator = new HtmlGenerator();
        return generator.getIndexPage(directory, rootDir).getBytes(Charset.forName("utf-8"));
    }

    public static byte[] readFile(File file) throws IOException {
        if (!file.isFile()) {
           return new byte[0];
        }

        byte[] fileBytes = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        inputStream.read(fileBytes);
        inputStream.close();

        return fileBytes;
    }

    private static Map<Integer, String> createCodesMap() {
        Map<Integer, String> codes = new HashMap<>();

        codes.put(200, "OK");
        codes.put(404, "Not Found");
        codes.put(301, "Moved Permanently");

        return Collections.unmodifiableMap(codes);
    }
}
