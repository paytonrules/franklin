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

//        response.put("HTTP-Version", request.get("HTTP-Version"));
        File file = normalizeFilename((String) request.get("Request-URI"));
        byte[] bytes;

        if (file.isFile()) {
            ResponseCode.twoHundred(response);
            bytes = FileReader.read(file);
        }
        else if (file.isDirectory()) {
            ResponseCode.twoHundred(response);
            bytes = DirectoryReader.read(file, rootDir);
            file = new File("directory.html");
        }
        else {
            ResponseCode.fourOhFour(response);
            file = new File(rootDir, "404.html");
            bytes = FileReader.read(file);
        }

        headers.put("Content-Type", URLConnection.guessContentTypeFromName(file.getName()));
        headers.put("Content-Length", String.valueOf(bytes.length));
        response.put("message-header", headers);
        response.put("message-body", bytes);

//        response.put("Content-Type", URLConnection.guessContentTypeFromName(file.getName()));
//        response.put("Content-Length", bytes.length);
//        response.put("Body", bytes);

        return response;
    }

    private File normalizeFilename(String filename) {
        return new File(rootDir, filename.substring(1));
    }
}
