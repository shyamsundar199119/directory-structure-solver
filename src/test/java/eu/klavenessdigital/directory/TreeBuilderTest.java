package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.service.TreeBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TreeBuilderTest {

    private TreeBuilder builder;

    @BeforeEach
    public void init(){
        builder = new TreeBuilder();
    }

    @Test
    void buildsTreeCorrectly() {
        Node root = new Node("1", null, null, "director", 0, null, null);
        Node child = new Node("2", "1", "file1", "file", 50, Classification.PUBLIC, "abc123");

        List<Node> nodes = Arrays.asList(root, child);

        Node builtRoot = builder.buildTree(nodes);

        assertEquals(50, builtRoot.getSize());
        assertEquals(1, builtRoot.getChildren().size());
        assertEquals("file1", builtRoot.getChildren().get(0).getName());
    }

    @Test
    void testThrowsIfNoRootNode() {
        // All nodes have a parent, so no root exists
        Node child1 = new Node("1", "file1.txt","99",  "file", 100, null, "sha1");
        Node child2 = new Node("2", "file2.txt","99",  "file", 200, null, "sha2");

        List<Node> nodes = List.of(child1, child2);

        assertThrows(IllegalStateException.class, () -> builder.buildTree(nodes),
                "Expected exception when no root node is present in CSV!");
    }
}

