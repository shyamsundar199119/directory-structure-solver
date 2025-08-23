package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.domain.Node;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

@Component
public class ExcelParser implements NodeParser {
    @Override
    public List<Node> parse(InputStream fileStream) throws IOException {
        // TODO: implement Excel parsing
        System.out.println("Parsing JSON file: ");
        return Collections.emptyList();
    }
}
