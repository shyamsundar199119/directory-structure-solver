package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Node;
import eu.klavenessdigital.directory.exception.TreeBuildingException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static eu.klavenessdigital.directory.util.ValidationUtil.isNullOrEmpty;
import static java.util.Objects.isNull;

/**
 * Service for building the tree
 *
 * Tasks covered:
 * - 3e: Calculation size of all files with classification "Public"
 */
@Service
public class TreeBuilder {

    public Node buildTree(List<Node> nodes) {
        Map<String, Node> nodeMap = new HashMap<>();
        Node root = null;

        // put all nodes in map
        for (Node n : nodes) {
            if (isNull(n.getId()) || n.getId().trim().isEmpty()) {
                throw new TreeBuildingException("Node found with null/empty ID: " + n);
            }

            if (nodeMap.containsKey(n.getId())) {
                throw new TreeBuildingException("Duplicate node ID detected: " + n.getId());
            }
            nodeMap.put(n.getId(), n);
        }

        // link children to parents
        for (Node n : nodes) {
            if (isNullOrEmpty(n.getParentId())) {
                root = n; // root found
            } else {
                Node parent = nodeMap.get(n.getParentId());
                if (parent != null) {
                    parent.addChild(n);
                } else {
                    throw new TreeBuildingException("Parent with id " + n.getParentId() + " not found for node " + n.getId());
                }
            }
        }

        if (root == null) {
            throw new TreeBuildingException("No root node found in CSV!");
        } else {
            root.calculateSize();
        }

        return root;
    }
}
