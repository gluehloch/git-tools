package de.awtools.git.browser;

import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Component;

import de.awtools.git.command.GitCommand;

@Component
public class GitRepositoryGate {

    private final GitRepositoryConfiguration gitRepositoryConfiguration;

    private final GitCommand gitCommand;

    public GitRepositoryGate(GitRepositoryConfiguration gitRepositoryConfiguration, GitCommand gitCommand) {
        this.gitRepositoryConfiguration = gitRepositoryConfiguration;
        this.gitCommand = gitCommand;
    }

    public Path rootPath() {
        return gitRepositoryConfiguration.getRepositoryPath();
    }

    public Optional<Path> resolve(String path) {
        final var filePath = gitRepositoryConfiguration.getRepositoryPath().resolve(path);
        final var normalizedPath = filePath.toAbsolutePath().normalize();

        // TODO Update variable...
        //gitCommand.clone("...", "...");

        if (normalizedPath.startsWith(gitRepositoryConfiguration.getRepositoryPath())) {
            return Optional.of(normalizedPath);
        } else {
            return Optional.empty();
        }
    }

}
