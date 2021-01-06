package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day9 {
    private static final Pattern DISTANCE_PATTERN = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

    public int handlePart1(Stream<String> input) {
        Map<String, Node> nodes = new HashMap<>();

        input.forEach(line -> {
            Matcher matcher = DISTANCE_PATTERN.matcher(line);

            if (matcher.matches()) {
                String start = matcher.group(1);
                String end = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                Node first = nodes.computeIfAbsent(start, Node::new);
                Node second = nodes.computeIfAbsent(end, Node::new);

                first.distances.put(second, distance);
                second.distances.put(first, distance);
            } else {
                throw new AssertionError("Line did not match regex! " + line);
            }
        });

        Node start = new Node("start");

        for (Node node : nodes.values()) {
            start.distances.put(node, 0);
        }

        List<Node> visitedNodes = new ArrayList<>();
        visitedNodes.add(start);

        return calculateRoutes(start, visitedNodes).stream()
                                                   .mapToInt(Integer::intValue)
                                                   .min()
                                                   .getAsInt();
    }

    public int handlePart2(Stream<String> input) {
        Map<String, Node> nodes = new HashMap<>();

        input.forEach(line -> {
            Matcher matcher = DISTANCE_PATTERN.matcher(line);

            if (matcher.matches()) {
                String start = matcher.group(1);
                String end = matcher.group(2);
                int distance = Integer.parseInt(matcher.group(3));

                Node first = nodes.computeIfAbsent(start, Node::new);
                Node second = nodes.computeIfAbsent(end, Node::new);

                first.distances.put(second, distance);
                second.distances.put(first, distance);
            } else {
                throw new AssertionError("Line did not match regex! " + line);
            }
        });

        Node start = new Node("start");

        for (Node node : nodes.values()) {
            start.distances.put(node, 0);
        }

        List<Node> visitedNodes = new ArrayList<>();
        visitedNodes.add(start);

        return calculateRoutes(start, visitedNodes).stream()
                                                   .mapToInt(Integer::intValue)
                                                   .max()
                                                   .getAsInt();
    }

    private List<Integer> calculateRoutes(Node node, List<Node> visitedNodes) {
        List<Integer> distances = new ArrayList<>();

        for (Node otherNode : node.distances.keySet()) {
            if (visitedNodes.contains(otherNode)) {
                continue;
            }

            List<Node> otherVisitedNodes = new ArrayList<>(visitedNodes);
            otherVisitedNodes.add(otherNode);
            List<Integer> otherDistances = calculateRoutes(otherNode, otherVisitedNodes);

            for (int distance : otherDistances) {
                distance += node.distances.get(otherNode);
                distances.add(distance);
            }
        }

        if (distances.isEmpty()) {
            distances.add(0);
        }

        return distances;
    }

    private static class Node {
        private final String name;
        private final Map<Node, Integer> distances;

        private Node(String name) {
            this.name = name;
            distances = new HashMap<>();
        }
    }
}
