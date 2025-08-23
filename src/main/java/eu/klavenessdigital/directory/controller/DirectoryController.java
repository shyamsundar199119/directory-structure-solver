package eu.klavenessdigital.directory.controller;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.parser.NodeParser;
import eu.klavenessdigital.directory.parser.NodeParserFactory;
import eu.klavenessdigital.directory.service.*;
import eu.klavenessdigital.directory.util.NodeFormatter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/directory")
public class DirectoryController {

    private final NodeParserFactory parserFactory;
    private final TreeRenderer treeRenderer;
    private final FolderFilterService folderFilterService;
    private final ClassificationFilterService classificationFilterService;
    private final SizeCalculator sizeCalculator;
    private Node cachedRoot = null;

    public DirectoryController(NodeParserFactory parserFactory,
                               TreeRenderer treeRenderer,
                               FolderFilterService folderFilterService,
                               ClassificationFilterService classificationFilterService,
                               SizeCalculator sizeCalculator) {
        this.parserFactory = parserFactory;
        this.treeRenderer = treeRenderer;
        this.folderFilterService = folderFilterService;
        this.classificationFilterService = classificationFilterService;
        this.sizeCalculator = sizeCalculator;
    }

    // --------------------------
    // 1. File Upload
    // --------------------------
    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();

        NodeParser parser = parserFactory.getParser(fileName);
        List<Node> nodes = parser.parse(file.getInputStream());
        TreeBuilder treeBuilder = new TreeBuilder();
        this.cachedRoot = treeBuilder.buildTree(nodes);;
        return "File uploaded and parsed successfully!";
    }

    private Node ensureRoot() {
        if (cachedRoot == null) throw new IllegalStateException("No CSV imported yet. POST /api/import first.");
        return cachedRoot;
    }

    /**
     * Render entire tree
     */
    @GetMapping("/tree")
    public String renderTree() {
        return treeRenderer.renderTree(ensureRoot());
    }

    /**
     * Get total size of all PUBLIC files
     */
    @GetMapping("/size/public")
    public Integer getPublicSize() {
        return sizeCalculator.sumPublicFiles(ensureRoot());
    }

    @GetMapping("/files/top-secret")
    public String topSecret() { return NodeFormatter.formatAsList(classificationFilterService.listFilesByClassification(ensureRoot(), Set.of(Classification.TOP_SECRET))); }


    @GetMapping("/files/secret")
    public String secret() { return NodeFormatter.formatAsList(classificationFilterService.listFilesByClassification(ensureRoot(), Set.of(Classification.SECRET))); }


    @GetMapping("/files/secret-or-top-secret")
    public String secretOrTop() { return NodeFormatter.formatAsList(classificationFilterService.listFilesByClassification(ensureRoot(), Set.of(Classification.SECRET, Classification.TOP_SECRET))); }


    /**
     * Get non-public files under a particular folder
     */
    @GetMapping("/files/non-public")
    public String getNonPublicUnderFolder(@RequestParam String folderName) {
        if (cachedRoot == null) {
            return "No tree cached. Upload a file first.";
        }
        try {
            return NodeFormatter.formatAsList(
                    folderFilterService.nonPublicUnderParticularFolder(cachedRoot, folderName)
            );
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
