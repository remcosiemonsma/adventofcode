package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Integer> {
    private static final Pattern DISTANCE_PATTERN = Pattern.compile("(\\w+) to (\\w+) = (\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var nodes = new HashMap<String, Node>();

        input.forEach(line -> processDistance(nodes, line));

        var start = new Node();

        for (var node : nodes.values()) {
            start.distances.put(node, 0);
        }

        var visitedNodes = new ArrayList<Node>();
        visitedNodes.add(start);

        return calculateRoutes(start, visitedNodes).stream()
                                                   .mapToInt(Integer::intValue)
                                                   .min()
                                                   .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var nodes = new HashMap<String, Node>();

        input.forEach(line -> processDistance(nodes, line));

        var start = new Node();

        for (var node : nodes.values()) {
            start.distances.put(node, 0);
        }

        var visitedNodes = new ArrayList<Node>();
        visitedNodes.add(start);

        return calculateRoutes(start, visitedNodes).stream()
                                                   .mapToInt(Integer::intValue)
                                                   .max()
                                                   .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private static void processDistance(HashMap<String, Node> nodes, String line) {
        Matcher matcher = DISTANCE_PATTERN.matcher(line);

        if (matcher.matches()) {
            String start = matcher.group(1);
            String end = matcher.group(2);
            int distance = Integer.parseInt(matcher.group(3));

            Node first = nodes.computeIfAbsent(start, t -> new Node());
            Node second = nodes.computeIfAbsent(end, t -> new Node());

            first.distances.put(second, distance);
            second.distances.put(first, distance);
        }
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
        private final Map<Node, Integer> distances;

        private Node() {
            distances = new HashMap<>();
        }
    }
}
