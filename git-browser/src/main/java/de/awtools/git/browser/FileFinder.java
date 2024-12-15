package de.awtools.git.browser;

import java.io.File;

public class FileFinder {

    public static File findFile(String name, File file) {
        File[] list = file.listFiles();
        if (list != null) {
            for (File fil : list) {
                if (fil.isDirectory()) {
                    return findFile(name, fil);
                } else if (name.equalsIgnoreCase(fil.getName())) {
                    return fil;
                }
            }
        }
        return null;
    }

}
