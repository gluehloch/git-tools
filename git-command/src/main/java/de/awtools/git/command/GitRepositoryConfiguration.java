package de.awtools.git.command;

import java.nio.file.Path;

public interface GitRepositoryConfiguration {

    Path getRepositoryPath();
    
    Path getPrivateKeyPath();

}
