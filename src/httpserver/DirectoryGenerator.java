package httpserver;

import java.io.File;

public class DirectoryGenerator {
    public static String indexPage(File directory, File rootDir) {
        if (!directory.isDirectory()) {
            return new String();
        }

        String page = "<!DOCTYPE HTML><html><head><title>%s</title></head><body>%s</body></html>";

        StringBuilder links = new StringBuilder(directory.list().length + 2);

        for (File file: directory.listFiles()) {
            links.append(String.format("<li>%s</li>", makeLink(file, rootDir)));
        }

        links.insert(0, "<ul>");
        links.append("</ul>");

        return String.format(page, directory.getName(), links);
    }

    public static String makeLink(File file, File rootDir) {
        String path = file.getAbsolutePath().replace(rootDir.getAbsolutePath(), "");
        String name = file.getName();
        return String.format("<a href=\"%s\">%s</a>", path, name);
    }
}
