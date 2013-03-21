package httpserver;

import java.io.File;
import java.util.Map;

public class HtmlGenerator {
    private String skeleton = "<!DOCTYPE HTML><html><head><title>%s</title></head><body>%s</body></html>";

    public String getIndexPage(File directory, File rootDir) {
        StringBuilder links = new StringBuilder(directory.list().length + 2);

        for (File file: directory.listFiles()) {
            links.append(String.format("<li>%s</li>", makeLink(file, rootDir)));
        }

        links.insert(0, "<ul>");
        links.append("</ul>");

        return String.format(skeleton, directory.getName(), links);
    }

    public String makeLink(File file, File rootDir) {
        String path = file.getAbsolutePath().replace(rootDir.getAbsolutePath(), "");
        String name = file.getName();
        return String.format("<a href=\"%s\">%s</a>", path, name);
    }

    public String getEchoPage(Map<String, String> entries, String format) {
        StringBuilder list = new StringBuilder(entries.size() + 2);

        for (String key: entries.keySet()) {
            list.append(String.format(format, key, entries.get(key)));
        }

        list.insert(0, "<ul>");
        list.append("</ul>");

        return String.format(skeleton, "Echo", list);
    }
}
