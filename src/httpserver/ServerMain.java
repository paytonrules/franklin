package httpserver;

import java.io.IOException;

public class ServerMain {
    public static void main(String[] args) {
//        int port = Integer.parseInt(args[1]);
//        String rootDir = args[3];
        Server httpServer;

        try {
            httpServer = new Server(5000, "/Users/nkw/git/cob_spec/public/");
            httpServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
