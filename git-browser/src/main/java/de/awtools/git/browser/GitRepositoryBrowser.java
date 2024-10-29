package de.awtools.git.browser;

import java.time.ZonedDateTime;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/repository")
public class GitRepositoryBrowser {

    @GetMapping("/browse")
    public String browse(@RequestParam("path") String path) {
        return ZonedDateTime.now().toString();
    }

}
