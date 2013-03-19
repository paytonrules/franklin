package tests.mocks;

import httpserver.sockets.HttpServerSocket;
import httpserver.sockets.HttpSocket;

import java.io.IOException;

public class ServerMocketWrapper implements HttpServerSocket {
    private ServerMocket serverMocket;

    public ServerMocketWrapper(ServerMocket serverMocket) {
        this.serverMocket = serverMocket;
    }

    @Override
    public HttpSocket accept() {
        return new MocketWrapper(serverMocket.accept());
    }

    @Override
    public void close() throws IOException {
        serverMocket.close();
    }

    @Override
    public boolean isBound() {
        return serverMocket.isBound();
    }

    @Override
    public boolean isClosed() {
        return serverMocket.isClosed();
    }
}
