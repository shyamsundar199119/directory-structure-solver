package eu.klavenessdigital.directory.util;

import eu.klavenessdigital.directory.domain.Node;

import java.util.Collection;
import java.util.stream.Collectors;

public class NodeFormatter {

    /** Converts a collection of nodes into newline-separated string using Node.toString() */
    public static String formatAsList(Collection<Node> nodes) {
        if (nodes == null || nodes.isEmpty()) {
            return "";
        }
        return nodes.stream()
                .map(Node::toString)
                .collect(Collectors.joining("\r\n"));
    }
}
