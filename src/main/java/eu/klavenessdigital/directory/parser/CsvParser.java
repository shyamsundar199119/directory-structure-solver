package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.util.Constants;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component("csvParser")
public class CsvParser implements NodeParser {

    private final String delimiter;
    private final boolean hasHeader;

    // default constructor → uses default delimiter from Constants
    public CsvParser() {
        this(Constants.DEFAULT_DELIMITER, Constants.HAS_HEADER);
    }

    // custom delimiter constructor
    public CsvParser(String delimiter, boolean hasHeader) {
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;
    }

    @Override
    public boolean supports(String fileName) {
        return fileName != null && fileName.toLowerCase().endsWith(".csv");
    }

    /**
     * Reads a CSV file and returns rows as list of String[].
     * - Skips header if configured
     * - Ignores comment lines (# or //)
     */
    @Override
    public List<Node> parse(InputStream fileStream) throws IOException {
        List<Node> nodes = new ArrayList<Node>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(fileStream, StandardCharsets.UTF_8))) {

            CSVFormat format = CSVFormat.DEFAULT
                    .withDelimiter(delimiter.charAt(0)) // Apache CSV needs single char delimiter
                    .withIgnoreEmptyLines(true)
                    .withTrim(true)
                    .withIgnoreSurroundingSpaces(true)
                    .withCommentMarker('#'); // ignores `#` as comment

            if (hasHeader) {
                format = format.withFirstRecordAsHeader();
            }

            try (CSVParser csvParser = new CSVParser(reader, format)) {
                for (CSVRecord record : csvParser) {
                    // Skip lines starting with //
                    if (record.get(0).startsWith("//")) {
                        continue;
                    }

                    String[] row = new String[record.size()];
                    for (int i = 0; i < record.size(); i++) {
                        row[i] = record.get(i);
                    }
                    nodes.add(NodeFactory.fromRow(row));
                }
            }
        }

        return nodes;
    }
}


