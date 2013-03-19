package tests.mocks;

import httpserver.sockets.HttpSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MocketWrapper implements HttpSocket {
    private Mocket mocket;

    public MocketWrapper(Mocket mocket) {
        this.mocket = mocket;
    }

    @Override
    public InputStream getInputStream() {
        return mocket.getInputStream();
    }

    @Override
    public OutputStream getOutputStream() {
        return mocket.getOutputStream();
    }

    @Override
    public void close() throws IOException {
        mocket.close();
    }
}
