package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.domain.Node;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface NodeParser {
    List<Node> parse(InputStream fileStream) throws IOException;
}
