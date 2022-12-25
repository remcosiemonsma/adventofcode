package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<String> {
    @Override
    public String handlePart1(Stream<String> input) {
        var lines = input.toList();

        var digits = new Character[][] {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}};

        var grid = new Grid<>(digits);

        var result = new StringBuilder();

        var position = new Coordinate(1, 1);

        for (var line : lines) {
            var steps = line.toCharArray();
            for (var step : steps) {
                switch (step) {
                    case 'U' -> {
                        var newPosition = new Coordinate(position.x(), position.y() - 1);
                        if (grid.isCoordinateInGrid(newPosition)) {
                            position = newPosition;
                        }
                    }
                    case 'R' -> {
                        var newPosition = new Coordinate(position.x() + 1, position.y());
                        if (grid.isCoordinateInGrid(newPosition)) {
                            position = newPosition;
                        }
                    }
                    case 'D' -> {
                        var newPosition = new Coordinate(position.x(), position.y() + 1);
                        if (grid.isCoordinateInGrid(newPosition)) {
                            position = newPosition;
                        }
                    }
                    case 'L' -> {
                        var newPosition = new Coordinate(position.x() - 1, position.y());
                        if (grid.isCoordinateInGrid(newPosition)) {
                            position = newPosition;
                        }
                    }
                    default -> throw new AssertionError("Eek!");
                }
            }
            result.append(grid.get(position));
        }

        return result.toString();
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var lines = input.toList();

        var digits = new Character[][] {{null, null, '1', null, null},
                                        {null, '2', '3', '4', null},
                                        {'5', '6', '7', '8', '9'},
                                        {null, 'A', 'B', 'C', null},
                                        {null, null, 'D', null, null}};

        var grid = new Grid<>(digits);

        var result = new StringBuilder();

        var position = new Coordinate(0, 2);

        for (var line : lines) {
            var steps = line.toCharArray();
            for (var step : steps) {
                switch (step) {
                    case 'U' -> {
                        var newPosition = new Coordinate(position.x(), position.y() - 1);
                        if (grid.get(newPosition) != null) {
                            position = newPosition;
                        }
                    }
                    case 'R' -> {
                        var newPosition = new Coordinate(position.x() + 1, position.y());
                        if (grid.get(newPosition) != null) {
                            position = newPosition;
                        }
                    }
                    case 'D' -> {
                        var newPosition = new Coordinate(position.x(), position.y() + 1);
                        if (grid.get(newPosition) != null) {
                            position = newPosition;
                        }
                    }
                    case 'L' -> {
                        var newPosition = new Coordinate(position.x() - 1, position.y());
                        if (grid.get(newPosition) != null) {
                            position = newPosition;
                        }
                    }
                    default -> throw new AssertionError("Eek!");
                }
            }
            result.append(grid.get(position));
        }

        return result.toString();
    }
}
