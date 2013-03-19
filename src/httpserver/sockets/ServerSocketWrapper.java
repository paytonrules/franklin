package httpserver.sockets;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerSocketWrapper implements HttpServerSocket {
    private ServerSocket serverSocket;

    public ServerSocketWrapper(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public HttpSocket accept() throws IOException {
        return new SocketWrapper(serverSocket.accept());
    }

    @Override
    public void close() throws IOException {
        serverSocket.close();
    }

    @Override
    public boolean isBound() {
        return serverSocket.isBound();
    }

    @Override
    public boolean isClosed() {
        return serverSocket.isClosed();
    }
}
