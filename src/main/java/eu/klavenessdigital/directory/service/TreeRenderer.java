package eu.klavenessdigital.directory.service;

import eu.klavenessdigital.directory.domain.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TreeRenderer {

    public String renderTree(Node root) {
        StringBuilder sb = new StringBuilder();
        renderNode(root, sb, 0);
        return sb.toString();
    }

    private void renderNode(Node node, StringBuilder sb, int depth) {
        indent(sb, depth);
        sb.append(node.toString()).append("\r\n");

        // sort children alphabetically
        List<Node> children = new ArrayList<>(node.getChildren());
        Collections.sort(children);

        for (Node child : children) {
            renderNode(child, sb, depth + 1);
        }
    }

    private void indent(StringBuilder sb, int depth) {
        for (int i = 0; i < depth; i++) {
            sb.append(" ");
        }
    }
}

