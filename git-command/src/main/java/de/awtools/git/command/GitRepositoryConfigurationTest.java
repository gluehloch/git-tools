package de.awtools.git.command;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("test")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "file:${user.home}/.awitools/.git-tools.properties",
})
public class GitRepositoryConfigurationTest extends AbstractGitRepositoryConfiguration {

}
