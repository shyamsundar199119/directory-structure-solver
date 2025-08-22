package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.util.FileUtil;

public class NodeParserFactory {

    public static NodeParser getParser(String filePath) {
        if (FileUtil.isCsvFile(filePath)) {
            return new CsvParser();
        } else if (FileUtil.isExcelFile(filePath)) {
            return new ExcelParser();
        }
        // fallback
        throw new IllegalArgumentException("Unsupported file format: " + filePath);
    }
}