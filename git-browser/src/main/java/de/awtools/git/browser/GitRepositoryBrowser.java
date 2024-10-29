package de.awtools.git.browser;

import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/repository")
public class GitRepositoryBrowser {

    @Value("${de.awtools.git.browser.repository.path}")
    private String repositoryPath;

    @GetMapping("/browse")
    public String browse(@RequestParam("path") String path) {
        return ZonedDateTime.now().toString();
    }

}
