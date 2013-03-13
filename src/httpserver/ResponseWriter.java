package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;

public class ResponseWriter {
    private static final byte[] CRLF = toUtfEightByteArray("\r\n");

    public static void write(Map<String, Object> response, OutputStream outputStream) throws  IOException {
        outputStream.write(toUtfEightByteArray((String) response.get("status-line")));
        outputStream.write(CRLF);

        Map<String, String> headers = (Map<String, String>) response.get("message-header");
        Iterator iterator = headers.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();
            String line = String.format("%s: %s", pair.getKey(), pair.getValue());
            outputStream.write(toUtfEightByteArray(line));
            outputStream.write(CRLF);
        }

        outputStream.write(CRLF);
        outputStream.write((byte[]) response.get("message-body"));
    }

    private static byte[] toUtfEightByteArray(String string) {
        return string.getBytes(Charset.forName("utf-8"));
    }
}
