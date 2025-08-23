package eu.klavenessdigital.directory.parser;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;

public class NodeFactory {

    // expects columns in fixed order:
    // id; parentId; name; type; size; classification; checksum
    public static Node fromRow(String[] values) {
        if (values.length < 7) {
            throw new IllegalArgumentException("Invalid row: " + String.join(";", values));
        }

        String id = values[0].trim();
        String parentId = values[1].trim().isEmpty() ? null : values[1].trim();
        String name = values[2].trim();
        String type = values[3].trim();
        int size = 0;
        try {
            size = Integer.parseInt(values[4].trim());
        } catch (NumberFormatException ignored) {}
        Classification classification = Classification.fromString(values[5].trim());
        String checksum = values[6].trim();

        return new Node(id, parentId, name, type, size, classification, checksum);
    }
}
