package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.FilterException;
import eu.klavenessdigital.directory.service.SizeCalculator;
import eu.klavenessdigital.directory.service.TreeBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class SizeCalculatorTest {

    private SizeCalculator sizeCalculator=new SizeCalculator();

    @Test
    void sumsOnlyPublicFiles() {
        Node root = new Node("1",  null, "root","directory", 0, null, null);
        Node file1 = new Node("2", "1","f1",  "file", 60, Classification.PUBLIC, "sha1");
        Node file2 = new Node("3", "1","f2",  "file", 40, Classification.SECRET, "sha2");

        List<Node> nodes = Arrays.asList(root, file1, file2);
        TreeBuilder builder = new TreeBuilder();

        Node builtRoot = builder.buildTree(nodes);

        assertEquals(60, sizeCalculator.sumPublicFiles(builtRoot));
    }

    @Test
    void testEmptyClassificationThrowsException() {
        Node invalidNode = new Node("1", null, "InvalidType", "file", 10, null, null);
        assertThrows(FilterException.class, () -> sizeCalculator.sumPublicFiles(invalidNode));
    }
}

