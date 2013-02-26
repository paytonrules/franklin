package httpserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class HttpResponse {
    public static void write(String response, OutputStream outputStream) throws IOException {
        outputStream.write(response.getBytes(Charset.forName("utf-8")));
        outputStream.close();
    }
}
