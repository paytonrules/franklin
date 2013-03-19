package httpserver;

import httpserver.sockets.ServerSocketWrapper;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
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

        int port = (int) parsedArgs.get("Port");

        try {
            httpServer = new Server(new ServerSocketWrapper(new ServerSocket(port)), router);
            httpServer.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setRoutes() {
        router.addRoute("filesystem", new FileSystemResponder(rootDir));
        router.addRoute("/redirect", new RedirectResponder("/"));

        router.addRoute("/parameters", new Responder() {
            @Override
            public Map<String, Object> respond(Map<String, Object> request) throws IOException {
                Map<String, Object> response = new HashMap<>();
                Map<String, String> headers = new HashMap<>();
                Map<String, String> parameters = (Map<String, String>) request.get("Parameters");

                String params = "";
                Iterator iterator = parameters.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    params += String.format("%s = %s\n", entry.getKey(), entry.getValue());
                }

                String html = "<DOCTYPE! HTML><html><body>%s</body></html>";
                byte[] bytes = String.format(html, params).getBytes(Charset.forName("utf-8"));
                response.put("message-body", bytes);

                Utilities.writeCommonHeaders(headers, "text/html", bytes.length);
                response.put("message-header", headers);

                Utilities.twoHundred(response);

                return response;
            }
        });
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
