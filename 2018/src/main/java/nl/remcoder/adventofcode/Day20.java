package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.*;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Integer> {
    private final Map<Coordinate, Node> nodeMap = new HashMap<>();

    @Override
    public Integer handlePart1(Stream<String> input) {
        var pattern = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var start = new Node(new Coordinate(0, 0));
        start.setShortestPath(0);
        nodeMap.put(start.getCoordinate(), start);

        findGraph(pattern, start);
        return nodeMap.values()
                      .stream()
                      .mapToInt(Node::getShortestPath)
                      .max()
                      .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var pattern = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var start = new Node(new Coordinate(0, 0));
        start.setShortestPath(0);
        nodeMap.put(start.getCoordinate(), start);

        findGraph(pattern, start);
        return (int) nodeMap.values()
                            .stream()
                            .filter(node -> node.getShortestPath() >= 1000)
                            .count();
    }

    private String getPart(String pattern, int index) {
        var stackIndex = 1;
        var current = index;

        while (stackIndex > 0) {
            current++;
            if (pattern.charAt(current) == '(') {
                stackIndex++;
            } else if (pattern.charAt(current) == ')') {
                stackIndex--;
            }
        }

        return pattern.substring(index + 1, current);
    }

    private void findGraph(String pattern, Node currentNode) {
        var start = currentNode;

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            if (c == 'N') {
                var nextNode = nodeMap.computeIfAbsent(currentNode.getCoordinate().above(), Node::new);
                if (nextNode.getShortestPath() > currentNode.getShortestPath() + 1) {
                    nextNode.setShortestPath(currentNode.getShortestPath() + 1);
                }
                currentNode = nextNode;
            } else if (c == 'E') {
                var nextNode = nodeMap.computeIfAbsent(currentNode.getCoordinate().right(), Node::new);
                if (nextNode.getShortestPath() > currentNode.getShortestPath() + 1) {
                    nextNode.setShortestPath(currentNode.getShortestPath() + 1);
                }
                currentNode = nextNode;
            } else if (c == 'S') {
                var nextNode = nodeMap.computeIfAbsent(currentNode.getCoordinate().below(), Node::new);
                if (nextNode.getShortestPath() > currentNode.getShortestPath() + 1) {
                    nextNode.setShortestPath(currentNode.getShortestPath() + 1);
                }
                currentNode = nextNode;
            } else if (c == 'W') {
                var nextNode = nodeMap.computeIfAbsent(currentNode.getCoordinate().left(), Node::new);
                if (nextNode.getShortestPath() > currentNode.getShortestPath() + 1) {
                    nextNode.setShortestPath(currentNode.getShortestPath() + 1);
                }
                currentNode = nextNode;
            } else if (c == '(') {
                var part = getPart(pattern, i);
                // + 1 because of brackets
                i += part.length() + 1;
                findGraph(part, currentNode);
            } else if (c == '|') {
                currentNode = start;
            }
        }
    }

    private static class Node {
        private final Coordinate coordinate;
        private int shortestPath = Integer.MAX_VALUE;

        public Coordinate getCoordinate() {
            return coordinate;
        }

        public int getShortestPath() {
            return shortestPath;
        }

        public void setShortestPath(int shortestPath) {
            this.shortestPath = shortestPath;
        }

        private Node(Coordinate coordinate) {
            this.coordinate = coordinate;
        }
    }
}
