package httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private String rootDir;
    private ServerSocket serverSocket;

    public HttpServer(int port, String rootDir) throws IOException {
        this.port = port;
        this.rootDir = rootDir;
        serverSocket = new ServerSocket(this.port);
    }

    public void run() throws IOException {
        while(!serverSocket.isClosed()) {
            Socket client = serverSocket.accept();
            String request = HttpRequest.parse(client.getInputStream());
            HttpResponse.write(ResponseGenerator.respond(request, 200, request), client.getOutputStream());
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
