package eu.klavenessdigital.directory.parser;

import java.io.IOException;
import java.util.List;

public interface NodeParser {
    List<String[]> parse(String filePath) throws IOException;
}
