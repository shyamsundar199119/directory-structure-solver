package eu.klavenessdigital.directory.util;

public class ValidationUtil {

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
