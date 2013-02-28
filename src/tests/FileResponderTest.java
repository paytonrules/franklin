package tests;

import httpserver.FileResponder;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class FileResponderTest {
    private String rootDir = System.getProperty("user.dir") + "/public";

    @Test
    public void testFileDoesNotExist() throws IOException {
        byte[] fileBytes = FileResponder.read("fake_file", rootDir);
        assertEquals(0, fileBytes.length);
    }

    @Test
    public void testFileDoesExist() throws IOException {
        File file = new File(rootDir + "/test.jpg");
        byte[] fileBytes = new byte[(int) file.length()];
        BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));

        for (int i = 0; i < fileBytes.length; i++) {
            fileBytes[i] = (byte) inputStream.read();
        }
        inputStream.close();

        assertTrue(Arrays.equals(fileBytes, FileResponder.read("/test.jpg", rootDir)));
    }
}
