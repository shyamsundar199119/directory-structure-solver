package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.FilterException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for filtering nodes by classification.
 *
 * Tasks covered:
 * - 3b: List files with classification "Top secret"
 * - 3c: List files with classification "Secret"
 * - 3d: List files with classification "Secret" or "Top secret"
 */
public class ClassificationFilterService {

    public List<Node> listFilesByClassification(Node root, Set<Classification> classifications) {
        List<Node> files = collectFiles(root);

        if (files.isEmpty()) {
            throw new FilterException("No file nodes found under the given root");
        }
//TODO Revisit logic to remove sorted() method
        return files.stream()
                .filter(n -> {
                    if (n.getClassification() == null) {
                        return false; // allow folders with null classification to be skipped
                    }
                    return classifications.contains(n.getClassification());
                })
                .sorted()
                .collect(Collectors.toList());
    }

    private List<Node> collectFiles(Node node) {
        List<Node> result = new ArrayList<>();
        if ("file".equalsIgnoreCase(node.getType())) {
            result.add(node);
        }
        for (Node child : node.getChildren()) {
            result.addAll(collectFiles(child));
        }
        return result;
    }
}
