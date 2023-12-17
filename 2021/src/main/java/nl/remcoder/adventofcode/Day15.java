package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .map(i -> Character.digit(i, 10))
                                   .toArray())
                        .toArray(int[][]::new);

        var map = new HashMap<Coordinate, Step>();

        for (var y = 0; y < grid.length; y++) {
            for (var x = 0; x < grid[y].length; x++) {
                var point = new Coordinate(x, y);
                var node = new Step(point);
                map.put(point, node);
            }
        }

        for (var point : map.keySet()) {
            var current = map.get(point);
            var above = map.get(point.above());
            var below = map.get(point.below());
            var right = map.get(point.right());
            var left = map.get(point.left());

            addNeighbors(grid, current, above);
            addNeighbors(grid, current, below);
            addNeighbors(grid, current, right);
            addNeighbors(grid, current, left);
        }

        var start = map.get(new Coordinate(0, 0));
        start.setDistance(0);
        var end = map.get(new Coordinate(grid[0].length - 1, grid.length - 1));

        return Dijkstra.findShortestDistance(List.of(start), node -> node == end)
                       .map(Node::getDistance)
                       .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .map(i -> Character.digit(i, 10))
                                   .toArray())
                        .toArray(int[][]::new);

        var newgrid = new int[grid.length * 5][grid[0].length * 5];

        for (var y = 0; y < grid.length; y++) {
            for (var x = 0; x < grid[y].length; x++) {
                newgrid[y][x] = grid[y][x];
                newgrid[y + grid.length][x] = getValue(grid[y][x], 1);
                newgrid[y + 2 * grid.length][x] = getValue(grid[y][x], 2);
                newgrid[y + 3 * grid.length][x] = getValue(grid[y][x], 3);
                newgrid[y + 4 * grid.length][x] = getValue(grid[y][x], 4);

                newgrid[y][x + grid[y].length] = getValue(grid[y][x], 1);
                newgrid[y + grid.length][x + grid[y].length] = getValue(grid[y][x], 2);
                newgrid[y + 2 * grid.length][x + grid[y].length] = getValue(grid[y][x], 3);
                newgrid[y + 3 * grid.length][x + grid[y].length] = getValue(grid[y][x], 4);
                newgrid[y + 4 * grid.length][x + grid[y].length] = getValue(grid[y][x], 5);

                newgrid[y][x + (2 * grid[y].length)] = getValue(grid[y][x], 2);
                newgrid[y + grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 3);
                newgrid[y + 2 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 4);
                newgrid[y + 3 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 4 * grid.length][x + (2 * grid[y].length)] = getValue(grid[y][x], 6);

                newgrid[y][x + (3 * grid[y].length)] = getValue(grid[y][x], 3);
                newgrid[y + grid.length][x + grid[y].length * 3] = getValue(grid[y][x], 4);
                newgrid[y + 2 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 3 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 6);
                newgrid[y + 4 * grid.length][x + (3 * grid[y].length)] = getValue(grid[y][x], 7);

                newgrid[y][x + (4 * grid[y].length)] = getValue(grid[y][x], 4);
                newgrid[y + grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 5);
                newgrid[y + 2 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 6);
                newgrid[y + 3 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 7);
                newgrid[y + 4 * grid.length][x + (4 * grid[y].length)] = getValue(grid[y][x], 8);
            }
        }

        var map = new HashMap<Coordinate, Step>();

        for (var y = 0; y < newgrid.length; y++) {
            for (var x = 0; x < newgrid[y].length; x++) {
                var point = new Coordinate(x, y);
                var node = new Step(point);
                map.put(point, node);
            }
        }

        for (var point : map.keySet()) {
            var current = map.get(point);
            var above = map.get(point.above());
            var below = map.get(point.below());
            var right = map.get(point.right());
            var left = map.get(point.left());

            addNeighbors(newgrid, current, above);
            addNeighbors(newgrid, current, below);
            addNeighbors(newgrid, current, right);
            addNeighbors(newgrid, current, left);
        }

        var start = map.get(new Coordinate(0, 0));
        start.setDistance(0);
        var end = map.get(new Coordinate(newgrid[0].length - 1, newgrid.length - 1));

        return Dijkstra.findShortestDistance(List.of(start), node -> node == end)
                       .map(Node::getDistance)
                       .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int getValue(int value, int increment) {
        int newValue = (value + increment) % 9;

        if (newValue == 0) {
            return 9;
        }
        return newValue;
    }

    private void addNeighbors(int[][] grid, Step current, Step other) {
        if (other != null) {
            current.addDestination(other, grid[other.position.y()][other.position.x()]);
            other.addDestination(current, grid[current.position.y()][current.position.x()]);
        }
    }

    private static class Step extends Node<Step> {
        private final Coordinate position;
        private final Map<Step, Long> adjacentNodes = new HashMap<>();

        public Step(Coordinate position) {
            this.position = position;
        }

        public void addDestination(Step destination, long distance) {
            adjacentNodes.put(destination, distance);
        }

        @Override
        public Map<Step, Long> getNeighbors() {
            return adjacentNodes;
        }

        @Override
        public void printStateInformation() {

        }
    }
}
