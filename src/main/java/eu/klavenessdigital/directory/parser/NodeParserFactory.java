package eu.klavenessdigital.directory.parser;

import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class NodeParserFactory {
    private final List<NodeParser> parsers;

    public NodeParserFactory(List<NodeParser> parsers) {
        this.parsers = parsers;
    }

    public NodeParser getParser(String fileName) {
        return parsers.stream()
                .filter(parser -> parser.supports(fileName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported file format: " + fileName));
    }
}