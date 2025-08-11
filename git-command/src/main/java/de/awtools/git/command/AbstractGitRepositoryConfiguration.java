package de.awtools.git.command;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

public abstract class AbstractGitRepositoryConfiguration implements GitRepositoryConfiguration  {

    @Value("${de.awtools.git.browser.repository.path}")
    private String repositoryPath;
    
    @Value("${de.awtools.git.browser.private.key.path}")
    private String privateKeyPath;

    private Path normalizedRepositoryPath;
    private Path normalizedPrivateKeyPath;

    @Override
    public Path getRepositoryPath() {
        if (normalizedRepositoryPath == null) {
            normalizedRepositoryPath = toPath(repositoryPath);
        }
        return normalizedRepositoryPath;    	
    }

	@Override
	public Path getPrivateKeyPath() {
        if (normalizedPrivateKeyPath == null) {
        	normalizedPrivateKeyPath = toPath(privateKeyPath);
        }
        return normalizedPrivateKeyPath;    			
	}

	private Path toPath(String path) {
		return Path.of(path).toAbsolutePath().normalize();
	}
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
}
