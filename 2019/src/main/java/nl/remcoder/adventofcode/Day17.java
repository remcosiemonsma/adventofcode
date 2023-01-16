package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> inputStream) throws InterruptedException {
        var line = inputStream.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var outputState = new LinkedBlockingQueue<Long>();

        var intCodeComputer = new IntCodeComputer(opcodes, null, outputState);

        intCodeComputer.runProgram();

        var stringBuilder = new StringBuilder();

        while (!outputState.isEmpty()) {
            var c = (char) outputState.take().intValue();

            stringBuilder.append(c);
        }

        var grid = stringBuilder.toString();

        var intersections = findIntersections(grid);

        return intersections.stream()
                            .mapToInt(point -> point.x * point.y)
                            .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) throws Exception {
        return null;
    }

    private List<Point> findIntersections(String gridString) {
        var intersections = new ArrayList<Point>();

        var grid = gridString.lines()
                             .map(String::toCharArray)
                             .toArray(char[][]::new);

        for (var y = 1; y < grid.length - 1; y++) {
            for (var x = 1; x < grid[0].length - 1; x++) {
                if (grid[y][x] == '#' && grid[y][x - 1] == '#' && grid[y][x + 1] == '#' && grid[y - 1][x] == '#' &&
                    grid[y + 1][x] == '#') {
                    var point = new Point(x, y);
                    intersections.add(point);
                }
            }
        }

        return intersections;
    }

    private record Point(int x, int y) {
    }
}
