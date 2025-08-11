package de.awtools.git.command;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Profile("production")
@Configuration
@PropertySource(ignoreResourceNotFound = true, value = {
        "file:${AWTOOLS_CONFDIR}/git-tools/git-tools.properties"
})
public class GitRepositoryConfigurationProduction extends AbstractGitRepositoryConfiguration {

}
