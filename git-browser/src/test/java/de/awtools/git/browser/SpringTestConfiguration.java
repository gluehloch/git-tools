package de.awtools.git.browser;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@ComponentScan({"de.awtools.git"})
@PropertySource(ignoreResourceNotFound = false, value = { "classpath:/application-test.properties" })
public class SpringTestConfiguration {

}
