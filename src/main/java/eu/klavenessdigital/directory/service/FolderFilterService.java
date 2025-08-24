package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Classification;
import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.FilterException;
import eu.klavenessdigital.directory.util.Constants;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FolderFilterService {

    public List<Node> nonPublicUnderParticularFolder(Node root, String folderName) {
        Node targetFolder = findNodeByName(root, folderName);
        if (targetFolder == null) {
            throw new FilterException("Folder with name '" + folderName + "' not found in tree");
        }

        List<Node> result = new ArrayList<>();
        collectNonPublic(targetFolder, result);

        if (result.isEmpty()) {
            throw new FilterException(
                    "No non-public files found under folder '" + folderName + "'"
            );
        }

        result.sort(Node::compareTo); // since Node is Comparable
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
        if (Constants.TYPE_FILE.equals(node.getType()) && (node.getClassification() == null || Classification.PUBLIC != node.getClassification())) {
            result.add(node);
        }
        for (Node child : node.getChildren()) {
            collectNonPublic(child, result);
        }
    }
}
