package tests;

import httpserver.HtmlGenerator;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HtmlGeneratorTest {
    private File rootDir;
    private HtmlGenerator generator;

    @Before
    public void setUp() {
        rootDir = new File(System.getProperty("user.dir"), "public");
        generator = new HtmlGenerator();
    }

    @Test
    public void testIndexHtml() {
        String page = generator.getIndexPage(rootDir, rootDir);

        for (File file: rootDir.listFiles()) {
            String link = generator.makeLink(file, rootDir);
            assertTrue(page.contains(link));
        }
    }

    @Test
    public void testLinkGeneration() {
        File file = new File(rootDir, "test.jpg");
        assertEquals("<a href=\"/test.jpg\">test.jpg</a>", generator.makeLink(file, rootDir));
    }

    @Test
    public void testEchoPage() {
        Map<String, String> values = new HashMap<>();
        values.put("Stuff", "and Junk");
        values.put("Test", "qwerty");

        String page = generator.getEchoPage(values, "%s = %s");

        for (String key: values.keySet()) {
            String value = String.format("%s = %s", key, values.get(key));
            assertTrue(page.contains(value));
        }
    }
}
