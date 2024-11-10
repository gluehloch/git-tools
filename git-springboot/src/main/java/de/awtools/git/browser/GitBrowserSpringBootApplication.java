package de.awtools.git.browser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = {"de.awtools.git" })
public class GitBrowserSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitBrowserSpringBootApplication.class, args);
    }
    
}
