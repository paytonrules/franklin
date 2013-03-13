package httpserver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ServerMain {
    private static Router router;
    private static File rootDir;

    public static void main(String[] args) {
        Map<String, Object> parsedArgs = parseArgs(args);

        rootDir = new File((String) parsedArgs.get("Root-Dir"));

        Server httpServer;
        router = new Router();

        setRoutes();

        try {
            httpServer = new Server((int) parsedArgs.get("Port"), router);
            httpServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setRoutes() {
        router.addRoute("filesystem", new FileSystemResponder(rootDir));
        router.addRoute("/redirect", new RedirectResponder("/"));
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
