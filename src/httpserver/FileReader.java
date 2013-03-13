package httpserver;

import java.io.*;

public class FileReader {

    public static byte[] read(File file) throws IOException {
        // Might be better to throw an error. We'll see...
        if (!file.isFile()) {
           return new byte[0];
        }

        byte[] fileBytes = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        inputStream.read(fileBytes);
        inputStream.close();

        return fileBytes;
    }
}
