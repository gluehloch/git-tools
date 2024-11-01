package de.awtools.git.command;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GitConfiguration {

    // "E:\\development\\projects\\tmp\\wiki.git",
    // "git@github.com:gluehloch/java-examples.wiki.git")
    @Value("de.awtools.git.command.repositoryPath")
    private String repositoryPath;

    @Value("de.awtools.git.command.remotePath")
    private String remotePath;

    @Value("de.awtools.git.command.privateKeyPath")
    private String privateKeyPath;

    public String getRepositoryPath() {
        return repositoryPath;
    }

    public String getRemotePath() {
        return remotePath;
    }

    public String getPrivateKeyPath() {
        return privateKeyPath;
    }

}
