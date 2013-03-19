package httpserver;

import httpserver.sockets.HttpServerSocket;
import httpserver.sockets.HttpSocket;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private HttpServerSocket serverSocket;
    private Router router;
    private ExecutorService threadPool;

    public Server(HttpServerSocket serverSocket, Router router) throws IOException {
        this.router = router;
        this.serverSocket = serverSocket;
        int cores = Runtime.getRuntime().availableProcessors();
        threadPool = Executors.newFixedThreadPool(cores);
    }

    public void run() throws IOException {
        while(!serverSocket.isClosed()) {
            HttpSocket client = serverSocket.accept();
            threadPool.execute(new RequestHandler(client, router));
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
