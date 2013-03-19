package httpserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

public class Utilities {
    public static void twoHundred(Map<String, Object> response) {
        response.put("status-line", statusLine(200, "OK"));
    }

    public static void fourOhFour(Map<String, Object> response) {
        response.put("status-line", statusLine(404, "Not Found"));
    }

    public static void threeOhOne(Map<String, Object> response) {
        response.put("status-line", statusLine(301, "Moved Permanently"));
    }

    public static void writeCommonHeaders(Map<String, String> headers, String type, int size) {
        headers.put("Content-Type", type);
        headers.put("Content-Length", String.valueOf(size));
        headers.put("Connection", "close");
        headers.put("Server", "Franklin-0.1");
    }

    private static String statusLine(int code, String phrase) {
        return String.format("HTTP/1.1 %d %s", code, phrase);
    }

    public static byte[] readDirAndGenerateHtml(File directory, File rootDir) throws IOException {
        if (!directory.isDirectory())
            return new byte[0];
        return DirectoryGenerator.indexPage(directory, rootDir).getBytes(Charset.forName("utf-8"));
    }

    public static byte[] readFile(File file) throws IOException {
        // Might be better to throw an error. We'll see...
        if (!file.isFile()) {
           return new byte[0];
        }

        byte[] fileBytes = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        inputStream.read(fileBytes);
        inputStream.close();

        return fileBytes;
    }
}
