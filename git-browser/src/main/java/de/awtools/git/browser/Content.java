package de.awtools.git.browser;

public class Content {

    private final String path;
    private final String fileName;

    public Content(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

}
