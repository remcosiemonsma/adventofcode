package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Stream;

public class Day17 {
    public int handlePart1(Stream<String> inputStream) throws InterruptedException {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> outputState = new LinkedBlockingQueue<>();

        IntCodeComputer intCodeComputer = new IntCodeComputer(opcodes, null, outputState);

        intCodeComputer.runProgram();

        StringBuilder stringBuilder = new StringBuilder();

        while (!outputState.isEmpty()) {
            char c = (char) outputState.take().intValue();

            stringBuilder.append(c);
        }

        String grid = stringBuilder.toString();

        System.out.println(grid);

        List<Point> intersections = findIntersections(grid);

        return intersections.stream()
                            .mapToInt(point -> point.x * point.y)
                            .sum();
    }

    private List<Point> findIntersections(String gridString) {
        List<Point> intersections = new ArrayList<>();

        String[] lines = gridString.split("\\n");

        char[][] grid = Arrays.stream(lines)
                                   .map(String::toCharArray)
                                   .toArray(char[][]::new);

        for (int y = 1; y < grid.length - 1; y++) {
            for (int x = 1; x < grid[0].length - 1; x++) {
                if (grid[y][x] == '#' && grid[y][x - 1] == '#' && grid[y][x + 1] == '#' && grid[y - 1][x] == '#' && grid[y + 1][x] == '#') {
                    Point point = new Point(x, y);
                    intersections.add(point);
                }
            }
        }

        return intersections;
    }

    private static class Point {
        private final int x;
        private final int y;

        private Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                   "x=" + x +
                   ", y=" + y +
                   '}';
        }
    }
}
