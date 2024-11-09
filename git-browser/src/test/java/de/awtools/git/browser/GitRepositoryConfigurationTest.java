package de.awtools.git.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfiguration.class})
class GitRepositoryConfigurationTest {

    @Autowired
    private GitRepositoryConfiguration gitRepositoryConfiguration;

    @Autowired
    private GitRepositoryGate gitRepositoryGate;

    @Test
    void gitRepositoryConfiguration() {
        assertThat(gitRepositoryConfiguration).isNotNull();
        assertThat(gitRepositoryConfiguration.getRepositoryPath()).isNotNull();
        System.out.println(gitRepositoryConfiguration.getRepositoryPath());
        System.out.println(gitRepositoryGate.resolve("home.md"));
        System.out.println(gitRepositoryGate.resolve("../../../home.md")); // Optional.empty
        System.out.println(gitRepositoryGate.resolve("./asdf/asdf/../../../../../home.md")); // Optional.empty
        System.out.println(gitRepositoryGate.resolve("E:/test.txt")); // Optional.empty
    }

}
