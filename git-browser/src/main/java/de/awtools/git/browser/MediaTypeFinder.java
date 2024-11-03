package de.awtools.git.browser;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;

public class MediaTypeFinder {

    public static MediaType findMediaType(String filename) {
        Optional<MediaType> mimeTypeOptional = MediaTypeFactory.getMediaType(filename);
        return mimeTypeOptional.orElse(MediaType.TEXT_PLAIN);
    }

    public static MediaType findMediaType2(String filename) {
        if (filename.endsWith(".html")) {
            return MediaType.TEXT_HTML;
        } else if (filename.endsWith(".md")) {
            return MediaType.TEXT_MARKDOWN;
        } else if (filename.endsWith(".json")) {
            return MediaType.APPLICATION_JSON;
        } else if (filename.endsWith(".xml")) {
            return MediaType.APPLICATION_XML;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (filename.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (filename.endsWith(".7z")) {
            return MediaType.parseMediaType("application/x-7z-compressed");
        } else if (filename.endsWith(".rar")) {
            return MediaType.parseMediaType("application/x-rar-compressed");
        } else if (filename.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        } else if (filename.endsWith(".csv")) {
            return MediaType.TEXT_PLAIN;
        }
        return null;
    }

}
