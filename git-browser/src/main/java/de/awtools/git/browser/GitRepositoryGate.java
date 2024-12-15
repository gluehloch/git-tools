package de.awtools.git.browser;

import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class GitRepositoryGate {

    private final GitRepositoryConfiguration gitRepositoryConfiguration;

    public GitRepositoryGate(GitRepositoryConfiguration gitRepositoryConfiguration) {
        this.gitRepositoryConfiguration = gitRepositoryConfiguration;
    }

    public Path rootPath() {
        return gitRepositoryConfiguration.getRepositoryPath();
    }

    public Optional<Path> resolve(String path) {
        final var filePath = gitRepositoryConfiguration.getRepositoryPath().resolve(path);
        final var normalizedPath = filePath.toAbsolutePath().normalize();

        if (normalizedPath.startsWith(gitRepositoryConfiguration.getRepositoryPath())) {
            return Optional.of(normalizedPath);
        } else {
            return Optional.empty();
        }
    }

}
