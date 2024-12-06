package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;
import nl.remcoder.adventofcode.library.model.Pair;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var guard = new Guard(grid.findValue('^')
                                  .orElseThrow(() -> new AssertionError("Eek!")),
                              Direction.UP,
                              grid);

        while (guard.isInGrid()) {
            guard.move();
        }

        return guard.visitedPlaces().size();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = GridFactory.createCharacterGridFromInput(input);

        var startingPosition = grid.findValue('^')
                                  .orElseThrow(() -> new AssertionError("Eek!"));

        var guard = new Guard(startingPosition,
                              Direction.UP,
                              grid);

        while (guard.isInGrid()) {
            guard.move();
        }

        var possibleObstructions = new HashSet<>(guard.visitedPlaces());
        possibleObstructions.remove(startingPosition);

        var validObstructions = new HashSet<>();

        for (var possibleObstruction : possibleObstructions) {
            grid.set(possibleObstruction, '#');

            var newGuard = new Guard(startingPosition,
                                  Direction.UP,
                                  grid);

            while (newGuard.isInGrid() && !newGuard.stuckInLoop()) {
                newGuard.recordStep();
                newGuard.move();
            }

            if (newGuard.stuckInLoop()) {
                validObstructions.add(possibleObstruction);
            }

            grid.set(possibleObstruction, '.');
        }

        return validObstructions.size();
    }

    private static class Guard {
        private Coordinate position;
        private Direction direction;
        private final Grid<Character> grid;
        private final Set<Coordinate> visitedPlaces = new HashSet<>();
        private final Set<Pair<Coordinate, Direction>> stepsTaken = new HashSet<>();

        public Guard(Coordinate position, Direction direction, Grid<Character> grid) {
            this.position = position;
            this.direction = direction;
            this.grid = grid;
            visitedPlaces.add(position);
        }

        public void move() {
            var next = position.getNeighbor(direction);

            while (grid.get(next) != null && grid.get(next) == '#') {
                direction = direction.rotateClockWise();
                next = position.getNeighbor(direction);
            }
            position = next;
            if (isInGrid()) {
                visitedPlaces.add(position);
            }
        }

        public boolean isInGrid() {
            return grid.isCoordinateInGrid(position);
        }

        public Set<Coordinate> visitedPlaces() {
            return visitedPlaces;
        }

        public void recordStep() {
            stepsTaken.add(new Pair<>(position, direction));
        }

        public boolean stuckInLoop() {
            return stepsTaken.contains(new Pair<>(position, direction));
        }
    }
}
