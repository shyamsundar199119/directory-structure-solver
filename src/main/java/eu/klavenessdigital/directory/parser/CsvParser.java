package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.util.Constants;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple CSV parser that skips comments and header.
 */
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

    /**
     * Reads a CSV file and returns rows as list of String[].
     * - Skips the header row.
     * - Ignores commented lines (starting with # or //).
     */
    public List<String[]> parse(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // skip empty
                if (line.startsWith("#") || line.startsWith("//")) continue; // skip comments

                if (hasHeader && !headerSkipped) {
                    headerSkipped = true; // skip first non-comment line (header)
                    continue;
                }

                String[] parts = line.split(delimiter, -1); // keep empty fields
                rows.add(parts);
            }
        }

        return rows;
    }
}

