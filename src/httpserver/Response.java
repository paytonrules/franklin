package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Map;

public class Response {
    private static final String CRLF = "\r\n";

    public static void write(Map<String, Object> response, OutputStream outputStream) throws  IOException {
        outputStream.write(toUtfEightByteArray(response.get("HTTP-Version") + " "));
        outputStream.write(toUtfEightByteArray(response.get("Status-Code") + " "));
        outputStream.write(toUtfEightByteArray((String) response.get("Reason-Phrase")));
        outputStream.write(toUtfEightByteArray(CRLF));
        outputStream.write(toUtfEightByteArray(CRLF));
//        outputStream.write((byte[]) response.get("Body"));
    }

    private static byte[] toUtfEightByteArray(String string) {

        return string.getBytes(Charset.forName("utf-8"));
    }
}
