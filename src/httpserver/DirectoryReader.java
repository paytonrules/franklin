package httpserver;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class DirectoryReader {
    public static byte[] read(File directory, File rootDir) throws IOException {
        if (!directory.isDirectory())
            return new byte[0];
        return DirectoryGenerator.indexPage(directory, rootDir).getBytes(Charset.forName("utf-8"));
    }
}
