package mbn.util;

import org.apache.commons.io.FilenameUtils;

public class ApplicationUtils {

    public static boolean isNotNullOrEmpty(String inputString) {
        return inputString != null && !inputString.isBlank() && !inputString.isEmpty() && !inputString.equals("undefined") && !inputString.equals("null") && !inputString.equals(" ");
    }
    public static String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }
}
