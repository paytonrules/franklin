package httpserver;

import httpserver.responders.FileSystemResponder;
import httpserver.responders.ParameterResponder;
import httpserver.responders.PutPostResponder;
import httpserver.responders.RedirectResponder;
import httpserver.sockets.ServerSocketWrapper;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {

    public static void main(String[] args) {
        Map<String, Object> parsedArgs = parseArgs(args);

        File rootDir = new File((String) parsedArgs.get("Root-Dir"));
        Router router = new Router();
        setRoutes(router, rootDir);

        Server httpServer;
        int port = (int) parsedArgs.get("Port");

        try {
            httpServer = new Server(new ServerSocketWrapper(new ServerSocket(port)), router);
            httpServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setRoutes(Router router, File rootDir) {
        router.addRoute("filesystem", new FileSystemResponder(rootDir));
        router.addRoute("/redirect", new RedirectResponder("/"));
        router.addRoute("/parameters", new ParameterResponder());
        router.addRoute("/form", new PutPostResponder());
    }

    private static Map<String, Object> parseArgs(String[] args) {
        Map<String, Object> parsedArgs = new HashMap<>();

        for (int i = 0; i < args.length; i += 2) {
            if (args[i].equals("-p"))
                parsedArgs.put("Port", Integer.parseInt(args[i+1]));
            else if (args[i].equals("-d"))
                parsedArgs.put("Root-Dir", args[i+1]);
        }

        return parsedArgs;
    }
}
