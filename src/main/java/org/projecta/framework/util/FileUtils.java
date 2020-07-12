package org.projecta.framework.util;

import java.io.File;
import java.net.URL;


public class FileUtils {

    /**
     * @param cls          Class Name
     * @param resourceName Resource Name
     * @return String
     */
    public static String getResourcePath(Class<?> cls, String resourceName) {
        ClassLoader classLoader = cls.getClassLoader();

        URL resource = classLoader.getResource(resourceName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found! - ");
        } else {
            return new File(resource.getFile()).getAbsolutePath();
        }
    }
}
