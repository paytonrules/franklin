package tests.mocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Mocket {
    private InputStream inputStream;
    private OutputStream outputStream;

    public Mocket(InputStream inputStream, OutputStream outputStream) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
