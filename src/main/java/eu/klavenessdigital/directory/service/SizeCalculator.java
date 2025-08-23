package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.FilterException;

import static eu.klavenessdigital.directory.util.Constants.TYPE_FILE;

/**
 * Service for calculation size of files with classification "Public".
 *
 * Tasks covered:
 * - 3e: Calculation size of all files with classification "Public"
 */

public class SizeCalculator {
    public static int sumPublicFiles(Node root) {
        if (TYPE_FILE.equalsIgnoreCase(root.getType())) {
            if (root.getClassification() == null) {
                throw new FilterException("File node '" + root.getName() + "' has no classification");
            }
            return Classification.PUBLIC == root.getClassification() ? root.getSize() : 0;
        }
        return root.getChildren().stream().mapToInt(SizeCalculator::sumPublicFiles).sum();
    }
}
