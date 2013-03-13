package tests;

import httpserver.DirectoryReader;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DirectoryReaderTest {
    private File rootDir;

    @Before
    public void setUp() {
        rootDir = new File(System.getProperty("user.dir"), "public");
    }

    @Test
    public void testDirectoryDoesNotExist() throws IOException {
        File file = new File("/stuff_and_junk/");
        byte[] bytes = DirectoryReader.read(file, rootDir);
        assertEquals(0, bytes.length);
    }

    @Test
    public void testDirectoryExists() throws IOException {
        File file = new File(System.getProperty("user.dir"), "public");
        byte[] bytes = DirectoryReader.read(file, rootDir);
        assertTrue(bytes.length > 0);
    }
}
