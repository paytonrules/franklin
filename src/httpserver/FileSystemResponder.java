package httpserver;

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
        Map<String, Object> response = new HashMap<>();
        Map<String, String> headers = new HashMap<>();

        File file;

        try {
            file = normalizeFilename((String) request.get("Request-URI"));
        }
        catch (NullPointerException e) {
            file = new File("");
        }

        byte[] bytes;

        if (file.isFile()) {
            Utilities.twoHundred(response);
            bytes = Utilities.readFile(file);
        }
        else if (file.isDirectory()) {
            Utilities.twoHundred(response);
            bytes = Utilities.readDirAndGenerateHtml(file, rootDir);
            file = new File("directory.html");
        }
        else {
            Utilities.fourOhFour(response);
            file = new File(rootDir, "404.html");
            bytes = Utilities.readFile(file);
        }

        Utilities.writeCommonHeaders(headers, URLConnection.guessContentTypeFromName(file.getName()), bytes.length);
        response.put("message-header", headers);
        response.put("message-body", bytes);

        return response;
    }

    private File normalizeFilename(String filename) {
        return new File(rootDir, filename.substring(1));
    }
}
