package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.util.NodeFormatter;

import java.util.Set;

import static eu.klavenessdigital.directory.domain.Classification.*;

public class DirectoryAnalysisService {

    private final TreeRenderer renderer = new TreeRenderer();
    private final ClassificationFilterService filterService = new ClassificationFilterService();
    private final FolderFilterService folderFilterService = new FolderFilterService();

    public void runAllTasks(Node root) {
        printIndentedTree(root);
        printTopSecretFiles(root);
        printSecretFiles(root);
        printSecretAndTopSecretFiles(root);
        printTotalSizeOfPublicFiles(root);
        printNonPublicFilesUnderFolder11(root);
    }

    // 3a) Render indented tree
    public void printIndentedTree(Node root) {
        System.out.println("=== Task 3a: Tree ===");
        System.out.println(renderer.renderTree(root));
    }

    // 3b) List Top secret files
    public void printTopSecretFiles(Node root) {
        System.out.println("=== Task 3b: Top Secret Files ===");
        System.out.println(NodeFormatter.formatAsList(filterService.listFilesByClassification(root, Set.of(TOP_SECRET))));

    }

    // 3c) List Secret files
    public void printSecretFiles(Node root) {
        System.out.println("=== Task 3c: Secret Files ===");
        System.out.println(NodeFormatter.formatAsList(filterService.listFilesByClassification(root, Set.of(SECRET))));
    }

    // 3d) List Secret OR Top secret
    public void printSecretAndTopSecretFiles(Node root) {
        System.out.println("=== Task 3d: Secret or Top Secret Files ===");
        System.out.println(NodeFormatter.formatAsList(filterService.listFilesByClassification(root, Set.of(SECRET, TOP_SECRET))));
    }

    // 3e) Sum of Public files
    public void printTotalSizeOfPublicFiles(Node root) {
        System.out.println("=== Task 3e: Sum Public Files ===");
        int sum = SizeCalculator.sumPublicFiles(root);
        System.out.println(sum);
    }

    // 3f) Non-public under folder11
    public void printNonPublicFilesUnderFolder11(Node root) {
        System.out.println("=== Task 3f: Non-Public under folder11 ===");
        System.out.println(NodeFormatter.formatAsList(folderFilterService.nonPublicUnderParticularFolder(root, "folder11")));
    }
}
