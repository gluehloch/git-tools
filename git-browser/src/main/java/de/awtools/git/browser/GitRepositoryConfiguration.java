package de.awtools.git.browser;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
public class GitRepositoryConfiguration {

    @Value("${de.awtools.git.browser.repository.path}")
    private String repositoryPath;

    private Path normalizedRepositoryPath;

    public Path getRepositoryPath() {
        if (normalizedRepositoryPath == null) {
            normalizedRepositoryPath = Path.of(repositoryPath).toAbsolutePath().normalize();
        }
        return normalizedRepositoryPath;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}