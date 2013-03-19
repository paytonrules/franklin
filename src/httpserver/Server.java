package httpserver;

import httpserver.sockets.HttpServerSocket;
import httpserver.sockets.HttpSocket;

import java.io.*;
import java.util.Map;

public class Server {
    private HttpServerSocket serverSocket;
    private Router router;

    public Server(HttpServerSocket serverSocket, Router router) throws IOException {
        this.router = router;
        this.serverSocket = serverSocket;
    }

    public void run() throws IOException {
        while(!serverSocket.isClosed()) {
            HttpSocket client = serverSocket.accept();
            Map<String, Object> request = RequestReader.parseHeader(client.getInputStream());
            Map<String, Object> response = router.route(request);
            ResponseWriter.write(response, client.getOutputStream());
            client.close();
        }
    }

    public boolean isBound() {
        return serverSocket.isBound();
    }

    public void close() throws IOException {
        serverSocket.close();
    }

    public boolean isClosed() {
        return serverSocket.isClosed();
    }
}
