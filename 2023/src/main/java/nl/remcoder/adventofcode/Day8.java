package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.math.Math;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day8 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var lines = input.toList();

        var path = lines.getFirst();

        var nodes = lines.stream()
                         .skip(2)
                         .map(this::mapToNode)
                         .collect(Collectors.toMap(Node::id, Function.identity()));

        nodes.values().forEach(node -> node.processNodes(nodes));

        var currentNode = nodes.get("AAA");

        var steps = 0L;
        var pointer = 0;

        while (!currentNode.id.equals("ZZZ")) {
            if (pointer == path.length()) {
                pointer = 0;
            }
            var step = path.charAt(pointer++);
            switch (step) {
                case 'L' -> currentNode = currentNode.left();
                case 'R' -> currentNode = currentNode.right();
                default -> throw new AssertionError("Eek!");
            }
            steps++;
        }

        return steps;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var path = lines.getFirst();

        var nodes = lines.stream()
                         .skip(2)
                         .map(this::mapToNode)
                         .collect(Collectors.toMap(Node::id, Function.identity()));

        nodes.values().forEach(node -> node.processNodes(nodes));

        var startNodes = nodes.values()
                              .stream()
                              .filter(this::isStartNode)
                              .toList();

        var distances = startNodes.stream()
                                  .map(node -> findDistance(node, path))
                                  .toList();

        return distances.stream()
                        .mapToLong(i -> (long) i)
                        .reduce(Math::lcm)
                        .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int findDistance(Node startNode, String path) {
        var currentNode = startNode;
        var steps = 0;
        var pointer = 0;

        while (!isEndNode(currentNode)) {
            if (pointer == path.length()) {
                pointer = 0;
            }
            var step = path.charAt(pointer++);
            switch (step) {
                case 'L' -> currentNode = currentNode.left();
                case 'R' -> currentNode = currentNode.right();
                default -> throw new AssertionError("Eek!");
            }
            steps++;
        }

        return steps;
    }

    private boolean isStartNode(Node node) {
        return node.id().charAt(2) == 'A';
    }

    private boolean isEndNode(Node node) {
        return node.id().charAt(2) == 'Z';
    }

    private Node mapToNode(String line) {
        var id = line.substring(0, 3);
        var leftId = line.substring(7, 10);
        var rightId = line.substring(12, 15);

        return new Node(id, leftId, rightId);
    }

    private static class Node {
        private final String id;
        private final String leftId;
        private final String rightId;
        private Node left;
        private Node right;

        private Node(String id, String leftId, String rightId) {
            this.id = id;
            this.leftId = leftId;
            this.rightId = rightId;
        }

        public String id() {
            return id;
        }

        public Node left() {
            return left;
        }

        public Node right() {
            return right;
        }

        public void processNodes(Map<String, Node> nodes) {
            left = nodes.get(leftId);
            right = nodes.get(rightId);
        }

        @Override
        public String toString() {
            return "Node{" + id + " = (" + leftId + ", " + rightId + ")}";
        }
    }
}
