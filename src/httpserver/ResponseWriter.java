package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Map;

public class ResponseWriter {
    private static final byte[] CRLF = toUtfEightByteArray("\r\n");

    public static void write(Map<String, Object> response, OutputStream outputStream) throws  IOException {
        try {
            outputStream.write(toUtfEightByteArray((String) response.get("status-line")));
            outputStream.write(CRLF);

            Map<String, String> headers = (Map<String, String>) response.get("message-header");
            for (String key: headers.keySet()) {
                String line = String.format("%s: %s", key, headers.get(key));
                outputStream.write(toUtfEightByteArray(line));
                outputStream.write(CRLF);
            }

            outputStream.write(CRLF);
            outputStream.write((byte[]) response.get("message-body"));
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }

    private static byte[] toUtfEightByteArray(String string) {
        return string.getBytes(Charset.forName("utf-8"));
    }
}
