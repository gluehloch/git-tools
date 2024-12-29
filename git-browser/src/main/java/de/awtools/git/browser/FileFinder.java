package de.awtools.git.browser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import de.awtools.git.browser.Content.Type;

public class FileFinder {

    private static final String IGNORE = ".git";

    public static File findFile(String name, File file) {
        File[] list = file.listFiles();
        File found = null;
        if (list != null) {
            for (File fil : list) {
                if (fil.isDirectory() && !fil.getName().equalsIgnoreCase(IGNORE)) {
                    found = findFile(name, fil);
                } else if (name.equalsIgnoreCase(fil.getName())) {
                    found = fil;
                }
                if (found != null) {
                    break;
                }
            }
        }
        return found;
    }

    public static List<Content> collectFiles(Path directory) throws Exception {
        List<Content> contents = new ArrayList<>();
        Files.walk(directory).forEach(path -> collectFiles(path.toFile(), contents));
        return contents;
    }

    public static void collectFiles(File file, List<Content> contents) {
        if (!file.isDirectory() && !file.getAbsolutePath().contains(IGNORE)) {
            contents.add(new Content(file.getParent(), file.getName(), toType(file.getName())));
        }
    }

    public static Type toType(String fileName) {
        final var fileNameLowerCase = fileName.toLowerCase();
        if (fileNameLowerCase.endsWith("md")) {
            return Type.MARKDOWN;
        } else if (fileNameLowerCase.endsWith("png")) {
            return Type.PNG;
        } else if (fileNameLowerCase.endsWith("txt")) {
            return Type.TXT;
        }
        return Type.UNKNWON;
    }

}
