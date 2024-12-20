package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.GridFactory;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Integer> {
    private int wantedCheatDistance;

    public void setWantedCheatDistance(int wantedCheatDistance) {
        this.wantedCheatDistance = wantedCheatDistance;
    }

    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = buildMaze(input);

        grid.findValues(Cell::isWall)
            .stream()
            .map(grid::get)
            .forEach(cell -> cell.setDistance(-1));

        var pathPositions = grid.findValues(Predicate.not(Cell::isWall));

        var cheats = 0;

        for (var pathPosition : pathPositions) {
            for (var neighbor : pathPosition.getStraightNeighbours()) {
                if (grid.get(neighbor).isWall()) {
                    for (var cheatPosition : neighbor.getStraightNeighbours()) {
                        if (grid.isCoordinateInGrid(cheatPosition)) {
                            var cheatDistance =
                                    grid.get(cheatPosition).distance() - grid.get(pathPosition).distance() - 2;
                            if (cheatDistance >= wantedCheatDistance) {
                                cheats++;
                            }
                        }
                    }
                }
            }
        }

        return cheats;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = buildMaze(input);

        grid.findValues(Cell::isWall)
            .stream()
            .map(grid::get)
            .forEach(cell -> cell.setDistance(-1));

        var pathPositions = grid.findValues(Predicate.not(Cell::isWall));

        var cheats = 0;

        for (var cheatStart : pathPositions) {
            for (var cheatEnd : pathPositions) {
                if (cheatStart.getDistanceTo(cheatEnd) <= 20) {
                    var cheatDistance = grid.get(cheatEnd).distance() - grid.get(cheatStart).distance() - cheatStart.getDistanceTo(cheatEnd);
                    if (cheatDistance >= wantedCheatDistance) {
                        cheats++;
                    }
                }
            }
        }

        return cheats;
    }

    private Grid<Cell> buildMaze(Stream<String> input) {
        var grid = GridFactory.createTypedGridFromInput(input, Cell::new);

        var start = grid.findValue(cell -> cell.value() == 'S')
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var cellsToInvestigate = Set.of(start);

        var currentDistance = 0;

        while (!cellsToInvestigate.isEmpty()) {
            var nextCellsToInvestigate = new HashSet<Coordinate>();

            for (var cellToInvestigate : cellsToInvestigate) {
                var cell = grid.get(cellToInvestigate);
                cell.setDistance(currentDistance);
                for (var neighbor : cellToInvestigate.getStraightNeighbours()) {
                    var nextCell = grid.get(neighbor);
                    if (!nextCell.isWall() && nextCell.distance() == Integer.MAX_VALUE) {
                        nextCellsToInvestigate.add(neighbor);
                    }
                }
            }

            currentDistance++;
            cellsToInvestigate = nextCellsToInvestigate;
        }
        return grid;
    }

    private static class Cell {
        private final char value;
        private int distance = Integer.MAX_VALUE;

        private Cell(int value) {
            this.value = (char) value;
        }

        public char value() {
            return value;
        }

        public int distance() {
            return distance;
        }

        public boolean isWall() {
            return value == '#';
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        @Override
        public String toString() {
            if (isWall()) {
                return "#";
            } else {
                return "" + distance % 10;
            }
        }
    }
}
