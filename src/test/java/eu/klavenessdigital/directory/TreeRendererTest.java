package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.service.TreeBuilder;
import eu.klavenessdigital.directory.service.TreeRenderer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TreeRendererTest {

    private TreeRenderer renderer;

    @BeforeEach
    void setUp() {
        renderer = new TreeRenderer();
    }

    @Test
    void testRenderSingleNode() {
        Node root = new Node("1", null, "root", "folder", 0, null, null);

        String output = renderer.renderTree(root);

        String expected = root.toString() + "\r\n"; // no indentation, only root
        assertEquals(expected, output);
    }

    @Test
    void rendersTreeIndentedAndSorted() {
        Node root = new Node("1", null, "root", "directory", 0, null, null);
        Node fileA = new Node("2", "1", "a.txt", "file", 10, Classification.PUBLIC, "c1");
        Node fileB = new Node("3", "1", "b.txt", "file", 20, Classification.PUBLIC, "c2");

        List<Node> nodes = Arrays.asList(root, fileB, fileA);
        TreeBuilder builder = new TreeBuilder();

        Node builtRoot = builder.buildTree(nodes);


        String output = renderer.renderTree(builtRoot);

        String expected =
                root.toString() + "\r\n" +
                        " " + fileA.toString() + "\r\n" +
                        " " + fileB.toString() + "\r\n";

        assertEquals(expected, output);
        assertTrue(output.indexOf("a.txt") < output.indexOf("b.txt")); // alphabetically ordered
    }
}

