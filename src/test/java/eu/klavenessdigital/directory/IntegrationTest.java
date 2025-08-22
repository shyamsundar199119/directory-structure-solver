package eu.klavenessdigital.directory;


import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.parser.NodeFactory;
import eu.klavenessdigital.directory.parser.NodeParser;
import eu.klavenessdigital.directory.parser.NodeParserFactory;
import eu.klavenessdigital.directory.service.ClassificationFilterService;
import eu.klavenessdigital.directory.service.FolderFilterService;
import eu.klavenessdigital.directory.service.TreeBuilder;
import eu.klavenessdigital.directory.service.TreeRenderer;
import eu.klavenessdigital.directory.util.NodeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;


class IntegrationTest {

    private Node root;
    private ClassificationFilterService classificationFilterService;
    private TreeRenderer renderer;
    private FolderFilterService folderFilterService;

    @BeforeEach
    public void init(){
        classificationFilterService = new ClassificationFilterService();
        renderer = new TreeRenderer();
        folderFilterService=new FolderFilterService();


        String filePath = "src/test/resources/directory-structure.csv";
        NodeParser parser = NodeParserFactory.getParser(filePath);
        List<String[]> rows = null;
        try {
            rows = parser.parse(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Node> nodes = new ArrayList<>();
        for (String[] row : rows) {
            // skip header if present
            if (row[0].equalsIgnoreCase("id")) continue;
            nodes.add(NodeFactory.fromCsvRow(row));
        }

        TreeBuilder treeBuilder = new TreeBuilder();
        root = treeBuilder.buildTree(nodes);
    }

    @Test
    void rendersTreeMatchesExpectedOutput() throws IOException {
        String actualOutput = renderer.renderTree(root);
        String expectedOutput = Files.readString(Path.of("src/test/resources/tree.txt"));

        assertEquals(expectedOutput.trim(), actualOutput.trim());
    }

    @Test
    void filtersTopSecretMatchesExpectedOutput() throws IOException {
        String actual = NodeFormatter.formatAsList(classificationFilterService.listFilesByClassification(root, Set.of(Classification.TOP_SECRET)));
        String expected = Files.readString(Path.of("src/test/resources/top-secret.txt"));

        assertEquals(expected.trim(), actual.trim());
    }
    @Test
    void filtersSecretAndTopSecretMatchesExpectedOutput() throws IOException {
        String actual = NodeFormatter.formatAsList(classificationFilterService.listFilesByClassification(root, Set.of(Classification.SECRET,Classification.TOP_SECRET)));
        String expected = Files.readString(Path.of("src/test/resources/secret-or-top-secret.txt"));

        assertEquals(expected.trim(), actual.trim());
    }

    @Test
    void filtersFolderMatchesExpectedOutput() throws IOException {
        String actual = NodeFormatter.formatAsList(folderFilterService.nonPublicUnderParticularFolder(root, "folder11"));
        String expected = Files.readString(Path.of("src/test/resources/non-public-folder11.txt"));

        assertEquals(expected.trim(), actual.trim());
    }


}

