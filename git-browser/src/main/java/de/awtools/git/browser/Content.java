package de.awtools.git.browser;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Content {

    public static enum Type {
        JSON, TXT, MARKDOWN, PNG, UNKNWON;
    }

    public static enum Status {
        PUBLISHED, DRAFT, ARCHIVED, DELETED;
    }

    private final String name;
    private final String shortName;
    private final String path;
    private final String fileName;
    private final Type type;
    private final Status status;

    @JsonCreator
    public Content(
            @JsonProperty("name") String name,
            @JsonProperty("shortName") String shortName,
            @JsonProperty("path") String path,
            @JsonProperty("fileName") String fileName,
            @JsonProperty("type") Type type,
            @JsonProperty("status") Status status) {
        this.name = name;
        this.shortName = shortName;
        this.path = path;
        this.fileName = fileName;
        this.type = type;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }

    public Status getStatus() {
        return status;
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

    @Override
    public String toString() {
        return "Content [name=" + name + ", shortName=" + shortName + ", path=" + path + ", fileName=" + fileName + ", type=" + type
                + ", status=" + status + "]";
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, name, path, shortName, status, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Content other = (Content) obj;
        return Objects.equals(fileName, other.fileName) && Objects.equals(name, other.name) && Objects.equals(path, other.path)
                && Objects.equals(shortName, other.shortName) && status == other.status && type == other.type;
    }

}
