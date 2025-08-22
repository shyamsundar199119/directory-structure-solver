package eu.klavenessdigital.directory.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static eu.klavenessdigital.directory.util.Constants.TYPE_FILE;

public class Node implements Comparable<Node> {
    private String id;
    private String parentId;
    private String name;
    private String type; // file or dir
    private int size;
    private Classification classification;
    private String checksum;
    private List<Node> children = new ArrayList<>();

    public Node(String id, String parentId, String name, String type, int size, Classification classification, String checksum) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
        this.type = type;
        this.size = size;
        this.classification = classification;
        this.checksum = checksum;
    }

    public String getId() { return id; }
    public String getParentId() { return parentId; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getSize() { return size; }
    public Classification getClassification() { return classification; }
    public String getChecksum() { return checksum; }
    public List<Node> getChildren() { return children; }
    public void addChild(Node child) { children.add(child); }

    /**
     * Calculate and assign size for directories.
     * For files, size stays as-is.
     */
    public int calculateSize() {
        if (TYPE_FILE.equalsIgnoreCase(type)) {
            return size;
        }
        this.size = children.stream()
                .mapToInt(Node::calculateSize)
                .sum();
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("name = ").append(name)
                .append(", type = ").append(TYPE_FILE.equalsIgnoreCase(type)?"File":"Directory")
                .append(", size = ").append(size);

        if ("file".equalsIgnoreCase(type)) {
            sb.append(", classification = ").append(classification.getLabel())
                    .append(", checksum = ").append(checksum);
        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        Node node = (Node) o;
        return Objects.equals(id, node.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Node other) {
        return this.name.compareToIgnoreCase(other.name);
    }
}
