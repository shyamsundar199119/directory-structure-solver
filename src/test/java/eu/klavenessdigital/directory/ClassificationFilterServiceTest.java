package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.service.ClassificationFilterService;
import eu.klavenessdigital.directory.service.TreeBuilder;
import eu.klavenessdigital.directory.util.NodeFormatter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ClassificationFilterServiceTest {
    @Test
    void filtersTopSecretFiles() {
        Node root = new Node("1", null , "root", "dir", 0, null, null);
        Node file = new Node("2", "1","secret.txt",  "file", 100, Classification.TOP_SECRET, "sha");

        List<Node> nodes = Arrays.asList(root, file);
        TreeBuilder builder = new TreeBuilder();

        Node builtRoot = builder.buildTree(nodes);

        ClassificationFilterService service = new ClassificationFilterService();
        String result = NodeFormatter.formatAsList(service.listFilesByClassification(builtRoot, Set.of(Classification.TOP_SECRET)));

        assertTrue(result.contains("secret.txt"));
        assertFalse(result.contains("root"));
    }
}
