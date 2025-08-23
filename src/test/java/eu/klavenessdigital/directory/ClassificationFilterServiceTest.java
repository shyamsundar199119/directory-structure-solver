package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.FilterException;
import eu.klavenessdigital.directory.service.ClassificationFilterService;
import eu.klavenessdigital.directory.service.TreeBuilder;
import eu.klavenessdigital.directory.util.NodeFormatter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static eu.klavenessdigital.directory.util.Constants.TYPE_FILE;
import static org.junit.jupiter.api.Assertions.*;


class ClassificationFilterServiceTest {

    private final ClassificationFilterService service = new ClassificationFilterService();

    @Test
    void filtersTopSecretFiles() {
        Node root = new Node("1", null , "root", "dir", 0, null, null);
        Node file = new Node("2", "1","secret.txt",  "file", 100, Classification.TOP_SECRET, "sha");

        List<Node> nodes = Arrays.asList(root, file);
        TreeBuilder builder = new TreeBuilder();

        Node builtRoot = builder.buildTree(nodes);

        String result = NodeFormatter.formatAsList(service.listFilesByClassification(builtRoot, Set.of(Classification.TOP_SECRET)));

        assertTrue(result.contains("secret.txt"));
        assertFalse(result.contains("root"));
    }

    @Test
    void testNullClassificationSetThrowsException() {
        Node root = new Node("1", null, "folder", "root", 0, null, null);
        assertThrows(FilterException.class,
                () -> service.listFilesByClassification(root, null));
    }

    @Test
    void testUnsupportedNodeTypeThrowsException() {
        Node root = new Node("1", null, "strange", "weirdNode", 0, Classification.PUBLIC, null);
        Set<Classification> set = new HashSet<>();
        set.add(Classification.PUBLIC);

        assertThrows(FilterException.class,
                () -> service.listFilesByClassification(root, set));
    }

    @Test
    void testNoFilesMatchReturnsEmptyList() {
        Node root = new Node("1", null, "folder", "root", 0, null, null);
        Node file = new Node("2", "1", "file1", TYPE_FILE, 10, Classification.SECRET, null);
        List<Node> nodes = Arrays.asList(root, file);
        TreeBuilder builder = new TreeBuilder();

        Node builtRoot = builder.buildTree(nodes);

        assertTrue(service.listFilesByClassification(builtRoot, Collections.singleton(Classification.PUBLIC)).isEmpty());
    }
}
