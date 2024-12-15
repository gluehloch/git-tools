package de.awtools.git.browser;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URL;

import org.junit.jupiter.api.Test;

class FileFinderTest {

    @Test
    void fileFinder() {
        URL resource = FileFinderTest.class.getResource("plaintext.txt");
        assertThat(resource).isNotNull();
        File file = new File(resource.getFile());
        File file2 = FileFinder.findFile("plaintext.txt", file.getParentFile());
        assertThat(file2).isNotNull();
        
        File file3 = FileFinder.findFile("findme.md", file.getParentFile());
        assertThat(file3).isNotNull();        
    }

}
