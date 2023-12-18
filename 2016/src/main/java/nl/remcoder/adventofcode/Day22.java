package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    private static final Pattern NODE_PATTERN =
            Pattern.compile("/dev/grid/node-x(\\d+)-y(\\d+) +(\\d+)T +(\\d+)T +(\\d+)T +(\\d+)%");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var nodes = input.map(NODE_PATTERN::matcher)
                         .filter(Matcher::matches)
                         .map(this::parseNode)
                         .toList();

        var pairs = 0;

        for (var node : nodes) {
            if (node.used() > 0) {
                for (var otherNode : nodes) {
                    if (node == otherNode) {
                        continue;
                    }
                    if (node.used() <= otherNode.available()) {
                        pairs++;
                    }
                }
            }
        }

        return pairs;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var nodes = input.map(NODE_PATTERN::matcher)
                         .filter(Matcher::matches)
                         .map(this::parseNode)
                         .toList();

        var x_size = nodes.stream()
                          .map(Node::coordinate)
                          .map(Coordinate::x)
                          .mapToInt(Long::intValue)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
        var y_size = nodes.stream()
                          .map(Node::coordinate)
                          .map(Coordinate::y)
                          .mapToInt(Long::intValue)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
        Node wallStart = null;
        Node hole = null;
        var nodeGrid = new Node[y_size + 1][x_size + 1];
        nodes.forEach(node -> nodeGrid[(int) node.coordinate().y()][(int) node.coordinate().x()] = node);
        for (var y = 0; y < nodeGrid.length; y++) {
            for (var x = 0; x < nodeGrid[y].length; x++) {
                Node n = nodeGrid[y][x];
                if (n.used == 0) {
                    hole = n;
                } else if (n.size() > 250) {
                    if (wallStart == null) {
                        wallStart = nodeGrid[y - 1][x];
                    }
                }
            }
        }

        assert hole != null;
        assert wallStart != null;

        var result = hole.coordinate().getDistanceTo(wallStart.coordinate());
        result += Math.abs(wallStart.coordinate().x() - x_size) + wallStart.coordinate().y() + 2;
        return (int) (result + 5 * (x_size - 1));
    }

    private Node parseNode(Matcher matcher) {
        return new Node(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                        Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4)),
                        Integer.parseInt(matcher.group(5)), Integer.parseInt(matcher.group(6)));
    }

    private record Node(Coordinate coordinate, int size, int used, int available, int percentage) {
    }
}
