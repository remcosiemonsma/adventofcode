package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.*;
import java.util.stream.Stream;

public class Day18 implements BiAdventOfCodeSolution<Long, String> {
    private int blocksToCatch;
    private int width;
    private int height;

    public void setBlocksToCatch(int blocksToCatch) {
        this.blocksToCatch = blocksToCatch;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = new Grid<Boolean>(0, 0, width, height);

        grid.fill(true);

        input.limit(blocksToCatch)
             .map(this::mapToCoordinate)
             .forEach(point -> grid.set(point, false));

        var foundSteps = new HashMap<Coordinate, Step>();

        var start = new Step(Coordinate.ORIGIN, grid, foundSteps);
        start.setDistance(0L);

        var end = new Coordinate(width, height);

        return Dijkstra.findShortestDistance(List.of(start),
                                             node -> ((Step) node).position().equals(end))
                       .orElseThrow(() -> new AssertionError("Eek!")).getDistance();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var blocks = input.map(this::mapToCoordinate)
                          .toList();

        var grid = new Grid<Boolean>(0, 0, width, height);

        grid.fill(true);

        blocks.stream()
              .limit(blocksToCatch)
              .forEach(point -> grid.set(point, false));

        var foundSteps = new HashMap<Coordinate, Step>();

        var start = new Step(Coordinate.ORIGIN, grid, foundSteps);
        start.setDistance(0L);

        var end = new Coordinate(width, height);

        var nextStepToBlock = blocksToCatch;

        while (Dijkstra.findShortestDistance(List.of(start),
                                             node -> ((Step) node).position().equals(end))
                       .isPresent()) {
            foundSteps.clear();
            nextStepToBlock++;
            var nextBlock = blocks.get(nextStepToBlock);
            grid.set(nextBlock, false);

            foundSteps = new HashMap<>();

            start = new Step(Coordinate.ORIGIN, grid, foundSteps);
        }

        System.out.println(grid);

        return blocks.get(nextStepToBlock).x() + "," + blocks.get(nextStepToBlock).y();
    }

    private static class Step extends Node<Step> {
        private final Coordinate position;
        private final Grid<Boolean> grid;
        private final Map<Coordinate, Step> foundSteps;

        private Step(Coordinate position, Grid<Boolean> grid, Map<Coordinate, Step> foundSteps) {
            this.position = position;
            this.grid = grid;
            this.foundSteps = foundSteps;
        }

        public Coordinate position() {
            return position;
        }

        @Override
        public Map<Step, Long> getNeighbors() {
            var neighbours = new HashMap<Step, Long>();

            position.getStraightNeighbours()
                    .stream()
                    .filter(grid::isCoordinateInGrid)
                    .filter(grid::get)
                    .map(coordinate -> foundSteps.computeIfAbsent(coordinate, key -> new Step(key, grid, foundSteps)))
                    .forEach(step -> neighbours.put(step, 1L));

            return neighbours;
        }

        @Override
        public void printStateInformation() {
            System.out.println(position);
        }
    }

    private Coordinate mapToCoordinate(String line) {
        var split = line.split(",");
        var x = Integer.parseInt(split[0]);
        var y = Integer.parseInt(split[1]);
        return new Coordinate(x, y);
    }
}
