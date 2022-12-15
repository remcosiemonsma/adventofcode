package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = parseGrid(input, 0);

        simulateSand(grid);

        return grid.countElements('o');
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = parseGrid(input, 2);

        for (int x = grid.getStartx(); x <= grid.getEndx(); x++) {
            grid.set(new Coordinate(x, grid.getEndy()), '#');
        }

        simulateSand(grid);

        return grid.countElements('o');
    }

    private void simulateSand(Grid<Character> grid) {
        var finished = false;

        while (!finished) {
            var sand = new Coordinate(500, 0);
            if (grid.get(sand) == 'o') {
                break;
            }
            var falling = true;
            while (falling) {
                var below = grid.get(sand.below());
                var belowLeft = grid.get(sand.bottomLeft());
                var belowRight = grid.get(sand.bottomRight());

                if (below != null && below == '.') {
                    sand = sand.below();
                    continue;
                } else if (belowLeft != null && belowLeft == '.') {
                    sand = sand.bottomLeft();
                    continue;
                } else if (belowRight != null && belowRight == '.') {
                    sand = sand.bottomRight();
                    continue;
                }

                if (below == null || belowLeft == null || belowRight == null) {
                    finished = true;
                    break;
                }

                grid.set(sand, 'o');
                falling = false;
            }
        }
    }

    private Grid<Character> parseGrid(Stream<String> input, int offset) {
        var walls = input.map(this::parseToCoordinates).toList();

        var maxX = walls.stream()
                        .mapToInt(wall -> wall.stream()
                                              .mapToInt(Coordinate::x)
                                              .max()
                                              .orElseThrow(() -> new AssertionError("Eek!")))
                        .max()
                        .orElseThrow(() -> new AssertionError("Ook!")) + 512;

        var minX = walls.stream()
                        .mapToInt(wall -> wall.stream()
                                              .mapToInt(Coordinate::x)
                                              .min()
                                              .orElseThrow(() -> new AssertionError("Eek!")))
                        .min()
                        .orElseThrow(() -> new AssertionError("Ook!")) - 512;

        var maxY = walls.stream()
                        .mapToInt(wall -> wall.stream()
                                              .mapToInt(Coordinate::y)
                                              .max()
                                              .orElseThrow(() -> new AssertionError("Eek!")))
                        .max()
                        .orElseThrow(() -> new AssertionError("Ook!")) + offset;

        var minY = 0;

        var grid = new Grid<Character>(minX, minY, maxX, maxY);

        grid.fill('.');

        for (var wall : walls) {
            for (int i = 0; i < wall.size() - 1; i++) {
                Coordinate start = wall.get(i);
                Coordinate end = wall.get(i + 1);

                start.getAllBetween(end)
                     .forEach(coordinate -> grid.set(coordinate, '#'));
            }
        }
        return grid;
    }

    private List<Coordinate> parseToCoordinates(String line) {
        return Arrays.stream(line.split(" -> "))
                     .map(this::parseToCoordinate)
                     .toList();
    }

    private Coordinate parseToCoordinate(String part) {
        String[] split = part.split(",");
        return new Coordinate(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }
}
