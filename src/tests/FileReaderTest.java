package tests;

import httpserver.FileReader;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileReaderTest {
    private File rootDir = new File(System.getProperty("user.dir"), "/public");
    private FileReader fileReader;

    @Before
    public void setUp() {
        fileReader = new FileReader();
    }

    @Test
    public void testFileDoesNotExist() throws IOException {
        File file = new File(rootDir, "fake_file");
        byte[] fileBytes = fileReader.read(file);
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

        assertTrue(Arrays.equals(fileBytes, fileReader.read(file)));
    }
}
