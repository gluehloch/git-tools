package de.awtools.git.browser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class MediaTypeFactoryTest {

    @Test
    void mediaTypeFactory() {
        assertThat(MediaTypeFinder.findMediaType2("home.md")).isEqualTo(MediaType.TEXT_MARKDOWN);
    }

}
