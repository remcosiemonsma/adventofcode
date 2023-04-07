package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var data = line.split(" ");

        var totalMetadata = 0;

        var readingNodeHeaderChildren = true;
        var readingNodeHeaderMetadata = false;
        var readingMetadata = false;

        var nodeId = 'A';

        var currentNode = new Node(String.valueOf(nodeId++));

        for (var entry : data) {
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

                    Node newNode = new Node(String.valueOf(nodeId++));
                    currentNode.childNodes.add(newNode);
                    newNode.parent = currentNode;

                    currentNode = newNode;

                } else if (currentNode.amountOfMetaDataEntries > 0) {
                    readingMetadata = true;
                }
                continue;
            }
            if (readingMetadata) {
                var metadataEntry = Integer.parseInt(entry);

                totalMetadata += metadataEntry;

                currentNode.metadataEntries.add(metadataEntry);

                if (currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size()) {
                    readingMetadata = false;
                    while (currentNode.amountOfChildNodes == currentNode.childNodes.size() && currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size() && currentNode.parent != null) {
                        //Look for first not fully read node
                        currentNode = currentNode.parent;
                    }
                    if (currentNode.amountOfChildNodes > currentNode.childNodes.size()) {
                        readingNodeHeaderChildren = true;

                        Node newNode = new Node(String.valueOf(nodeId++));
                        currentNode.childNodes.add(newNode);
                        newNode.parent = currentNode;

                        currentNode = newNode;
                    } else {
                        readingMetadata = true;
                    }
                }
            }
        }
        
        return totalMetadata;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));
        
        var data = line.split(" ");
        
        var readingNodeHeaderChildren = true;
        var readingNodeHeaderMetadata = false;
        var readingMetadata = false;

        var nodeId = 'A';
        
        var root = new Node(String.valueOf(nodeId++));

        var currentNode = root;

        for (var entry : data) {
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

                    Node newNode = new Node(String.valueOf(nodeId++));
                    currentNode.childNodes.add(newNode);
                    newNode.parent = currentNode;

                    currentNode = newNode;

                } else if (currentNode.amountOfMetaDataEntries > 0) {
                    readingMetadata = true;
                }
                continue;
            }
            if (readingMetadata) {
                var metadataEntry = Integer.parseInt(entry);

                currentNode.metadataEntries.add(metadataEntry);

                if (currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size()) {
                    readingMetadata = false;
                    while (currentNode.amountOfChildNodes == currentNode.childNodes.size() && currentNode.amountOfMetaDataEntries == currentNode.metadataEntries.size() && currentNode.parent != null) {
                        //Look for first not fully read node
                        currentNode = currentNode.parent;
                    }
                    if (currentNode.amountOfChildNodes > currentNode.childNodes.size()) {
                        readingNodeHeaderChildren = true;

                        Node newNode = new Node(String.valueOf(nodeId++));
                        currentNode.childNodes.add(newNode);
                        newNode.parent = currentNode;

                        currentNode = newNode;
                    } else {
                        readingMetadata = true;
                    }
                }
            }
        }
        
        return determineNodeValue(root);
    }

    private int determineNodeValue(Node node) {
        if (node.amountOfChildNodes == 0) {
            return node.metadataEntries.stream().mapToInt(value -> value).sum();
        } else {
            var value = 0;
            for (var index : node.metadataEntries) {
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
