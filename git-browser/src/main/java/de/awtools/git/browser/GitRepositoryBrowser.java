package de.awtools.git.browser;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@CrossOrigin
@RestController
@RequestMapping("/repository")
public class GitRepositoryBrowser {

    @Value("${de.awtools.git.browser.repository.path}")
    private String repositoryPath;

    @GetMapping("/browse")
    @ResponseBody
    public ResponseEntity<InputStreamResource> browse(@RequestParam("path") String path) {
        final var filePath = Path.of(repositoryPath, path);
        final var file = filePath.toFile();
        if (file.isDirectory()) {

        } else if (file.exists()) {
            try {
                System.out.println("File: " + file.getAbsolutePath());
                
                Optional<MediaType> mimeTypeOptional = MediaTypeFactory.getMediaType(file.getName());
                InputStream is = new FileInputStream(file);
                return ResponseEntity.ok().contentType(mimeTypeOptional.get()).body(new InputStreamResource(is));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        ByteArrayInputStream is = new ByteArrayInputStream(ZonedDateTime.now().toString().getBytes());
        return  ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(new InputStreamResource(is));
    }

}
