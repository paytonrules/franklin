package httpserver.sockets;

import java.io.IOException;

public interface HttpServerSocket {
    public HttpSocket accept() throws IOException;
    public void close() throws IOException;
    public boolean isBound();
    public boolean isClosed();
}
