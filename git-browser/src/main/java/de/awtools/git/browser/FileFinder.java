package de.awtools.git.browser;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.awtools.git.browser.Content.Type;

public class FileFinder {

    private static final String IGNORE_GIT = ".git";
    private static final String IGNORE_JSONX = ".jsonx";

    public static File findFile(String name, File file) {
        File[] list = file.listFiles();
        File found = null;
        if (list != null) {
            for (File fil : list) {
                if (fil.isDirectory() && !fil.getName().equalsIgnoreCase(IGNORE_GIT)) {
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
        Files.walk(directory).forEach(path -> collectFiles(directory, path.toFile(), contents));
        return contents;
    }

    public static void collectFiles(Path directory, File file, List<Content> contents) {
        if (!file.isDirectory()
                && !file.getAbsolutePath().contains(IGNORE_GIT)
                && !file.getName().endsWith(IGNORE_JSONX)) {

            final var jsonxFileName = file.getName().replaceAll("\\.\\w+$", IGNORE_JSONX);
            final var jsonFile = new File(file.getParent(), jsonxFileName);

            if (jsonFile.exists()) {
                try {
                    final var mapper = new ObjectMapper();
                    final var content = mapper.readValue(new FileInputStream(jsonFile), Content.class);
                    contents.add(new Content(
                            content.getName(),
                            content.getShortName(),
                            getParentPath(directory, file),
                            file.getName(),
                            toType(file.getName()),
                            content.getStatus()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }

    public static String replaceEnding(String fileName) {
        return fileName.replaceAll("\\.\\w+$", ".jsonx");
    }

    public static String getParentPath(Path rootDirectory, File file) {
        String parentPath = file.getParent().substring(rootDirectory.toString().length());
        if (parentPath != null) {
            parentPath = parentPath.replace("\\", "/");
        }
        return parentPath;
    }

    public static Type toType(String fileName) {
        final var fileNameLowerCase = fileName.toLowerCase();
        if (fileNameLowerCase.endsWith("json")) {
            return Type.JSON;
        } else if (fileNameLowerCase.endsWith("md")) {
            return Type.MARKDOWN;
        } else if (fileNameLowerCase.endsWith("png")) {
            return Type.PNG;
        } else if (fileNameLowerCase.endsWith("txt")) {
            return Type.TXT;
        }
        return Type.UNKNWON;
    }

}
