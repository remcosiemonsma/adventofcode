package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.pathfinding.Dijkstra;
import nl.remcoder.adventofcode.library.pathfinding.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Integer> {
    private static final Map<Coordinate, Step> STEPS = new HashMap<>();
    private Coordinate desiredPosition = new Coordinate(0, 0);

    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = generateGrid(input);

        var start = new Step(new Coordinate(1, 1), grid);
        start.setDistance(0);

        return (int) Dijkstra.findShortestDistance(start, node -> ((Step) node).currentPosition.equals(desiredPosition))
                             .orElseThrow(() -> new AssertionError("Eek!"))
                             .getDistance();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = generateGrid(input);

        var visitedCoordinates = new HashSet<Coordinate>();

        var currentCoordinates = new HashSet<Coordinate>();

        currentCoordinates.add(new Coordinate(1, 1));

        for (var i = 0; i < 50; i++) {
            var newCoordinates = new HashSet<Coordinate>();
            for (var coordinate : currentCoordinates) {
                visitedCoordinates.add(coordinate);
                for (var newCoordinate : coordinate.getStraightNeighbours()) {
                    if (Boolean.TRUE.equals(grid.get(newCoordinate))) {
                        newCoordinates.add(newCoordinate);
                    }
                }
            }
            currentCoordinates = newCoordinates;
        }

        visitedCoordinates.addAll(currentCoordinates);

        return visitedCoordinates.size();
    }

    private Grid<Boolean> generateGrid(Stream<String> input) {
        var number = input.mapToInt(Integer::parseInt)
                          .findFirst()
                          .orElseThrow(() -> new AssertionError("Eek!"));

        var walls = new Boolean[100][100];

        for (int y = 0; y < 100; y++) {
            for (int x = 0; x < 100; x++) {
                walls[y][x] = isOpenSpace(x, y, number);
            }
        }

        return new Grid<>(walls);
    }

    private boolean isOpenSpace(int x, int y, int favoriteNumber) {
        var result = x * x + 3 * x + 2 * x * y + y + y * y;
        result += favoriteNumber;

        var binaryString = Integer.toBinaryString(result);

        var numberOfOnes = binaryString.chars().filter(c -> c == '1').count();

        return numberOfOnes % 2 == 0;
    }

    public void setDesiredPosition(Coordinate desiredPosition) {
        this.desiredPosition = desiredPosition;
    }

    private static class Step extends Node {
        private final Coordinate currentPosition;

        private final Grid<Boolean> grid;

        private Step(Coordinate currentPosition, Grid<Boolean> grid) {
            this.currentPosition = currentPosition;
            this.grid = grid;
        }

        @Override
        public Map<? extends Node, Long> getNeighbors() {
            var neighbors = new HashMap<Step, Long>();

            for (var coordinate : currentPosition.getStraightNeighbours()) {
                if (Boolean.TRUE.equals(grid.get(coordinate))) {
                    neighbors.put(STEPS.computeIfAbsent(coordinate, coordinate1 -> new Step(coordinate1, grid)), 1L);
                }
            }

            return neighbors;
        }

        @Override
        public void printStateInformation() {

        }

    }
}
