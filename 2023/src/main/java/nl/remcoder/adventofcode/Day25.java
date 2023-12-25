package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Pair;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {
    private static final Comparator<Map.Entry<Pair<String, String>, Integer>> COMPARATOR = Comparator.comparingInt(
            Map.Entry::getValue);

    @Override
    public Integer handlePart1(Stream<String> input) {
        var nodes = new HashSet<String>();
        var connectionsMap = new HashMap<String, Set<String>>();
        var connections = new HashSet<Pair<String, String>>();

        input.forEach(line -> processLine(line, nodes, connectionsMap, connections));

        var distances = connections.stream()
                                   .collect(Collectors.toMap(Function.identity(),
                                                             connection -> findDistance(connection, connectionsMap)));

        var edgesToRemove = distances.entrySet().stream().sorted(COMPARATOR.reversed()).limit(3).map(Map.Entry::getKey).toList();

        for (var edgeToRemove : edgesToRemove) {
            connectionsMap.get(edgeToRemove.left()).remove(edgeToRemove.right());
            connectionsMap.get(edgeToRemove.right()).remove(edgeToRemove.left());
        }

        return countNodes(edgesToRemove.getFirst().left(), connectionsMap) * countNodes(edgesToRemove.getFirst().right(), connectionsMap);
    }

    @Override
    public Integer handlePart2(Stream<String> input) throws Exception {
        return null;
    }

    private void processLine(String line, Set<String> nodes, Map<String, Set<String>> connectionsMap,
                             Set<Pair<String, String>> connections) {
        var firstsplit = line.split(": ");
        nodes.add(firstsplit[0]);
        var secondsplit = firstsplit[1].split(" ");
        var nodeConnections = connectionsMap.computeIfAbsent(firstsplit[0], s -> new HashSet<>());
        nodeConnections.addAll(Arrays.asList(secondsplit));
        for (var connection : secondsplit) {
            nodes.add(connection);
            var otherConnections = connectionsMap.computeIfAbsent(connection, s -> new HashSet<>());
            otherConnections.add(firstsplit[0]);
            connections.add(new Pair<>(firstsplit[0], connection));
        }
    }

    private int findDistance(Pair<String, String> edge, Map<String, Set<String>> connectionsMap) {
        var tempMap = new HashMap<String, Set<String>>();

        for (var key : connectionsMap.keySet()) {
            tempMap.put(key, new HashSet<>(connectionsMap.get(key)));
        }

        tempMap.get(edge.left()).remove(edge.right());
        tempMap.get(edge.right()).remove(edge.left());

        var steps = 1;

        var current = tempMap.get(edge.left());
        while (!current.contains(edge.right())) {
            var newCurrent = new HashSet<String>();
            for (var node : current) {
                newCurrent.addAll(tempMap.get(node));
            }
            steps++;
            current = newCurrent;
        }

        return steps;
    }

    private int countNodes(String start, Map<String, Set<String>> connectionsMap) {
        var nodes = new HashSet<String>();
        nodes.addAll(connectionsMap.get(start));
        var previousSize = 0;
        var newSize = nodes.size();

        while (previousSize != newSize) {
            previousSize = newSize;
            var newNodes = new HashSet<>(nodes);
            for (var node : nodes) {
                newNodes.addAll(connectionsMap.get(node));
            }
            nodes = newNodes;
            newSize = newNodes.size();
        }

        return nodes.size();
    }
}
