package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.TreeBuildingException;
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
    void testDuplicateNodeIdThrowsException() {
        Node n1 = new Node("1", null, "folder", "root", 0, null,null );
        Node n2 = new Node("1", "root", "file", "dupFile", 5, Classification.PUBLIC,null );

        List<Node> nodes = Arrays.asList(n1, n2);

        assertThrows(TreeBuildingException.class, () -> builder.buildTree(nodes));
    }

    @Test
    void testNoRootNodeThrowsException() {
        Node file = new Node("2", "99", "file", "file1", 10, Classification.PUBLIC,null);
        List<Node> nodes = Arrays.asList(file);

        assertThrows(TreeBuildingException.class, () -> builder.buildTree(nodes));
    }
}

