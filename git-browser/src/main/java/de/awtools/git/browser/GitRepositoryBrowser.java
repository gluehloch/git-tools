package de.awtools.git.browser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/repository")
public class GitRepositoryBrowser {

    private static Logger LOG = LoggerFactory.getLogger(GitRepositoryBrowser.class);

    private final GitRepositoryGate repository;

    public GitRepositoryBrowser(final GitRepositoryGate repository) {
        this.repository = repository;
    }

    @GetMapping("/browse")
    @ResponseBody
    public ResponseEntity<InputStreamResource> browse(@RequestParam("path") String path) {
        final var resolvedPath = repository.resolve(path);

        if (resolvedPath.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return toResource(resolvedPath.get().toFile());
    }

    @GetMapping("/find")
    @ResponseBody
    public ResponseEntity<InputStreamResource> find(@RequestParam("file") String fileName) {
        final var rootPath = repository.rootPath();
        if (!fileName.endsWith(".md")) {
            fileName = fileName + ".md";
        }
        final var file = FileFinder.findFile(fileName, rootPath.toFile());
        return toResource(file);
    }

    private ResponseEntity<InputStreamResource> toResource(File file) {
        if (file.isDirectory()) {
            // TODO
        } else if (file.exists()) {
            try {
                LOG.atDebug().log("File: {}", file.getAbsolutePath());

                Optional<MediaType> mimeTypeOptional = MediaTypeFactory.getMediaType(file.getName());
                InputStream is = new FileInputStream(file);
                return ResponseEntity.ok().contentType(mimeTypeOptional.orElse(MediaType.TEXT_PLAIN)).body(new InputStreamResource(is));
            } catch (FileNotFoundException ex) {
                LOG.atError().setCause(ex).log("File note found:");
            }
        }
        return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body(new InputStreamResource(toDefaultTime()));
    }

    private InputStream toDefaultTime() {
        return new ByteArrayInputStream(ZonedDateTime.now().toString().getBytes());
    }

}
