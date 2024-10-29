package de.awtools.git.browser;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/respository")
public class GitRepositoryBrowser {

    @GetMapping("/browse")
    public String browse(@RequestParam String path) {
        return new String();
    }

}
