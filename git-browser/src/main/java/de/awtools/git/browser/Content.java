package de.awtools.git.browser;

public class Content {

    public static enum Type {
        TXT, MARKDOWN, PNG, UNKNWON;
    }

    private final String path;
    private final String fileName;
    private final Type type;

    public Content(String path, String fileName, Type type) {
        this.path = path;
        this.fileName = fileName;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public Type getType() {
        return type;
    }

}
