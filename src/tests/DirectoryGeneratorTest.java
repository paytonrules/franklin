package tests;

import httpserver.DirectoryGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DirectoryGeneratorTest {
    private File rootDir;

    @Before
    public void setUp() {
        rootDir = new File(System.getProperty("user.dir"), "public");
    }

    @Test
    public void testIndexHtml() {
        String page = DirectoryGenerator.indexPage(rootDir, rootDir);

        for (File file: rootDir.listFiles()) {
            String link = DirectoryGenerator.makeLink(file, rootDir);
            assertTrue(page.contains(link));
        }
    }

    @Test
    public void testLinkGeneration() {
        File file = new File(rootDir, "test.jpg");
        assertEquals("<a href=\"/test.jpg\">test.jpg</a>", DirectoryGenerator.makeLink(file, rootDir));
    }
}
