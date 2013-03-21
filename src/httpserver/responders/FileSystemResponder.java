package httpserver.responders;

import httpserver.Utilities;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class FileSystemResponder implements Responder {
    private File rootDir;

    public FileSystemResponder(File rootDir) {
        this.rootDir = rootDir;
    }

    @Override
    public Map<String, Object> respond(Map<String, Object> request) throws IOException {
        Map<String, String> headers = new HashMap<>();
        File file;

        try {
            file = normalizeFilename((String) request.get("Request-URI"));
        }
        catch (NullPointerException e) {
            file = new File("");
        }

        byte[] body;

        String statusLine;
        if (file.isFile()) {
            statusLine = Utilities.statusLine(200);
            body = Utilities.readFile(file);
        }
        else if (file.isDirectory()) {
            statusLine = Utilities.statusLine(200);
            body = Utilities.readDirAndGenerateHtml(file, rootDir);
            file = new File("directory.html");
        }
        else {
            statusLine = Utilities.statusLine(404);
            file = new File(rootDir, "404.html");
            body = Utilities.readFile(file);
        }

        Utilities.writeCommonHeaders(headers, URLConnection.guessContentTypeFromName(file.getName()), body.length);

        return Utilities.generateResponse(statusLine, headers, body);
    }

    private File normalizeFilename(String filename) {
        return new File(rootDir, filename.substring(1));
    }
}
