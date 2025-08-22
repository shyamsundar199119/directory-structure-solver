package eu.klavenessdigital.directory.parser;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class ExcelParser implements NodeParser {
    @Override
    public List<String[]> parse(String filePath) throws IOException {
        // TODO: implement Excel parsing
        System.out.println("Parsing JSON file: " + filePath);
        return Collections.emptyList();
    }
}
