package mbn.util;

public class ApplicationUtils {

    public final static String PATH = "/Users/albica/Desktop/mbn/mbn-service/src/main/resources/static/documents/";

    public static boolean isNotNullOrEmpty(String inputString) {
        return inputString != null && !inputString.isBlank() && !inputString.isEmpty() && !inputString.equals("undefined") && !inputString.equals("null") && !inputString.equals(" ");
    }
}
