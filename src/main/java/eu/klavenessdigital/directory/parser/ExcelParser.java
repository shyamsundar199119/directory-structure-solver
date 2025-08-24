package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.domain.Node;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Component("excelParser")
public class ExcelParser implements NodeParser {

    @Override
    public boolean supports(String fileName) {
        return fileName != null &&
                (fileName.toLowerCase().endsWith(".xls") || fileName.toLowerCase().endsWith(".xlsx"));
    }
    @Override
    public List<Node> parse(InputStream fileStream) throws IOException {
        // TODO: implement Excel parsing
        System.out.println("Parsing JSON file: ");
        return Collections.emptyList();
    }
}
