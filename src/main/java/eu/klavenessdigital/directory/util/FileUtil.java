package eu.klavenessdigital.directory.util;

public class FileUtil {

    public static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
    }

    public static boolean isCsvFile(String fileName) {
        return "csv".equalsIgnoreCase(getFileExtension(fileName));
    }

    public static boolean isExcelFile(String fileName) {
        return "xls".equalsIgnoreCase(getFileExtension(fileName));
    }
}
