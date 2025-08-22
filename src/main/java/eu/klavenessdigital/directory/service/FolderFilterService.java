package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FolderFilterService {
    public List<Node> nonPublicUnderParticularFolder(Node root, String folderName) {
        List<Node> result = new ArrayList<>();
        Node folder11 = findNodeByName(root, folderName);
        if (folder11 != null) {
            collectNonPublic(folder11, result);
        }
        result.sort(Comparator.comparing(Node::getName));
        return result;
    }

    private Node findNodeByName(Node node, String name) {
        if (name.equals(node.getName())) return node;
        for (Node child : node.getChildren()) {
            Node found = findNodeByName(child, name);
            if (found != null) return found;
        }
        return null;
    }

    private void collectNonPublic(Node node, List<Node> result) {
        if ("file".equals(node.getType()) && (node.getClassification() == null || Classification.PUBLIC != node.getClassification())) {
            result.add(node);
        }
        for (Node child : node.getChildren()) {
            collectNonPublic(child, result);
        }
    }
}
