package httpserver;

import httpserver.sockets.HttpSocket;

import java.io.IOException;
import java.util.Map;

public class RequestHandler implements Runnable {
    private HttpSocket socket;
    private Router router;

    public RequestHandler(HttpSocket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        try {
            Map<String, Object> request = Request.parseRequest(socket.getInputStream());
            Map<String, Object> response = router.route(request);
            ResponseWriter.write(response, socket.getOutputStream());
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
