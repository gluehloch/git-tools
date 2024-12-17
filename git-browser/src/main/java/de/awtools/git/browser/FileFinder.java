package de.awtools.git.browser;

import java.io.File;

public class FileFinder {

    public static File findFile(String name, File file) {
        File[] list = file.listFiles();
        File found = null;
        if (list != null) {
            for (File fil : list) {
                if (fil.isDirectory()) {
                    found = findFile(name, fil);
                } else if (name.equalsIgnoreCase(fil.getName())) {
                    found = fil;
                }
                if (found != null) {
                    break;
                }
            }
        }
        return found;
    }

}
