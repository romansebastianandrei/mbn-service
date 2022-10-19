package mbn.util;

public class ApplicationUtils {

    public static boolean isNotNullOrEmpty(String inputString) {
        return inputString != null && !inputString.isBlank() && !inputString.isEmpty() && !inputString.equals("undefined") && !inputString.equals("null") && !inputString.equals(" ");
    }
}
