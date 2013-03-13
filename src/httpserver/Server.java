package httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Router router;

    public Server(int port, Router router) throws IOException {
        this.port = port;
        this.router = router;

        serverSocket = new ServerSocket(this.port);
    }

    public void run() throws IOException {
        while(!serverSocket.isClosed()) {
            Socket client = serverSocket.accept();
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
