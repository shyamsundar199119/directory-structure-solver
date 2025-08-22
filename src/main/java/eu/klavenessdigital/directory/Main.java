package eu.klavenessdigital.directory;

import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.parser.NodeFactory;
import eu.klavenessdigital.directory.parser.NodeParser;
import eu.klavenessdigital.directory.parser.NodeParserFactory;
import eu.klavenessdigital.directory.service.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        try {
            String filePath = "src/test/resources/directory-structure.csv";


            NodeParser parser = NodeParserFactory.getParser(filePath);
            List<String[]> rows = parser.parse(filePath);

            List<Node> nodes = new ArrayList<>();
            for (String[] row : rows) {
                // skip header if present
                if (row[0].equalsIgnoreCase("id")) continue;
                nodes.add(NodeFactory.fromCsvRow(row));
            }

            TreeBuilder treeBuilder = new TreeBuilder();
            Node root = treeBuilder.buildTree(nodes);

            DirectoryAnalysisService service = new DirectoryAnalysisService();
            service.runAllTasks(root);
        } catch (IllegalStateException e) {
            System.err.println("Error while building tree: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading CSV: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error running application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}