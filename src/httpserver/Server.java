package httpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class Server {
    private int port;
    private String rootDir;
    private ServerSocket serverSocket;
    private Router router;

    public Server(int port, String rootDir) throws IOException {
        this.port = port;
        this.rootDir = rootDir;
        router = new Router();

        // This is temporary.
        router.addRoute("/");
        serverSocket = new ServerSocket(this.port);
    }

    public void run() throws IOException {
        while(!serverSocket.isClosed()) {
            Socket client = serverSocket.accept();
            Map<String, Object> request = Request.parseHeader(client.getInputStream());
            Response.write(router.route(request), client.getOutputStream());
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
