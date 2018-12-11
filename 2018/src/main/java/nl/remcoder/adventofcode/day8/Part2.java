package nl.remcoder.adventofcode.day8;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.lines(Paths.get(ClassLoader.getSystemResource("day8/input").toURI())).findFirst().get();
        String[] data = line.split(" ");

        boolean readingNodeHeaderChildren = true;
        boolean readingNodeHeaderMetadata = false;
        boolean readingMetadata = false;

        char nodeId = 'A';

        Node root = new Node("" + nodeId++);

        Node currentNode = root;

        for (String entry : data) {
            if (readingNodeHeaderChildren) {
                currentNode.amountOfChildNodes = Integer.parseInt(entry);
                readingNodeHeaderChildren = false;
                readingNodeHeaderMetadata = true;
                continue;
            }
            if (readingNodeHeaderMetadata) {
                currentNode.amountOfMetaDataEntries = Integer.parseInt(entry);
                readingNodeHeaderMetadata = false;

                if (currentNode.amountOfChildNodes > 0) {
                    readingNodeHeaderChildren = true;

                    Node newNode = new Node("" + nodeId++);
                    currentNode.childNodes.add(newNode);
                    newNode.parent = currentNode;

                    currentNode = newNode;

                } else if (currentNode.amountOfMetaDataEntries > 0) {
                    readingMetadata = true;
                }
                continue;
            }
            if (readingMetadata) {
                int metadataEntry = Integer.parseInt(entry);

                currentNode.metadataEntries.add(metadataEntry);

                if (currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size()) {
                    readingMetadata = false;
                    while (currentNode.amountOfChildNodes == currentNode.childNodes.size() && currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size() && currentNode.parent != null) {
                        //Look for first not fully read node
                        currentNode = currentNode.parent;
                    }
                    if (currentNode.amountOfChildNodes > currentNode.childNodes.size()) {
                        readingNodeHeaderChildren = true;

                        Node newNode = new Node("" + nodeId++);
                        currentNode.childNodes.add(newNode);
                        newNode.parent = currentNode;

                        currentNode = newNode;
                    } else {
                        readingMetadata = true;
                    }
                }
            }
        }

        System.out.println(root);

        System.out.println(determineNodeValue(root));
    }

    private static int determineNodeValue(Node node) {
        if (node.amountOfChildNodes == 0) {
            return node.metadataEntries.stream().mapToInt(value -> value).sum();
        } else {
            int value = 0;
            for (int index : node.metadataEntries) {
                if (index <= node.childNodes.size()) {
                    value += determineNodeValue(node.childNodes.get(index - 1));
                }
            }
            return value;
        }
    }

    private static class Node {
        final String nodeId;
        int amountOfChildNodes;
        int amountOfMetaDataEntries;
        List<Node> childNodes = new ArrayList<>();
        List<Integer> metadataEntries = new ArrayList<>();
        Node parent;

        private Node(String nodeId) {
            this.nodeId = nodeId;
        }

        @Override
        public String toString() {
            return "Node{" +
                   "nodeId='" + nodeId + '\'' +
                   ", amountOfChildNodes=" + amountOfChildNodes +
                   ", amountOfMetaDataEntries=" + amountOfMetaDataEntries +
                   ", childNodes=" + childNodes +
                   ", metadataEntries=" + metadataEntries +
                   '}';
        }
    }
}
