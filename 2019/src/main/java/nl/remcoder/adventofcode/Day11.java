package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.ConsumingQueue;
import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class Day11 {
    private int ypos;
    private int xpos;
    private Direction direction;
    private boolean[][] grid;
    private Set<Point> paintedPoints;
    private OutputState outputState = OutputState.PAINT;

    public int handlePart1(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> output = new ConsumingQueue(outputValue -> {
            switch (outputState) {
                case PAINT -> {
                    paint(outputValue);
                    outputState = OutputState.DIRECTION;
                }
                case DIRECTION -> {
                    rotate(outputValue);
                    move();
                    outputState = OutputState.PAINT;
                }
            }
        });
        BlockingQueue<Long> input = new ProducingQueue(() -> grid[ypos][xpos] ? 1L : 0L);

        grid = new boolean[96][96];

        ypos = 48;
        xpos = 48;

        direction = Direction.UP;

        paintedPoints = new HashSet<>();

        IntCodeComputer robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        printGrid();

        return paintedPoints.size();
    }

    private void printGrid() {
        for (boolean[] line : grid) {
            for (boolean pixel : line) {
                if (pixel) {
                    System.out.print('#');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
    }

    public int handlePart2(Stream<String> inputStream) {
        String line = inputStream.findFirst().orElseThrow(AssertionError::new);

        long[] opcodes = Arrays.stream(line.split(","))
                               .mapToLong(Long::parseLong)
                               .toArray();

        BlockingQueue<Long> output = new ConsumingQueue(aLong -> {
            switch (outputState) {
                case PAINT -> {
                    paint(aLong);
                    outputState = OutputState.DIRECTION;
                }
                case DIRECTION -> {
                    rotate(aLong);
                    move();
                    outputState = OutputState.PAINT;
                }
            }
        });
        BlockingQueue<Long> input = new ProducingQueue(() -> grid[ypos][xpos] ? 1L : 0L);

        grid = new boolean[96][96];

        ypos = 48;
        xpos = 48;

        direction = Direction.UP;

        grid[ypos][xpos] = true;

        paintedPoints = new HashSet<>();

        IntCodeComputer robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        printGrid();

        return paintedPoints.size();
    }

    private void rotate(Long direction) {
        if (direction == 0) {
            rotateLeft();
        } else if (direction == 1) {
            rotateRight();
        }
    }

    private void paint(Long color) {
        paintedPoints.add(new Point(xpos, ypos));
        if (color == 0) {
            grid[ypos][xpos] = false;
        } else if (color == 1) {
            grid[ypos][xpos] = true;
        }
    }

    private void move() {
        switch (direction) {
            case UP -> ypos--;
            case LEFT -> xpos--;
            case DOWN -> ypos++;
            case RIGHT -> xpos++;
        }
    }

    private void rotateLeft() {
        switch (direction) {
            case UP -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.UP;
        }
    }

    private void rotateRight() {
        switch (direction) {
            case UP -> direction = Direction.RIGHT;
            case RIGHT -> direction = Direction.DOWN;
            case DOWN -> direction = Direction.LEFT;
            case LEFT -> direction = Direction.UP;
        }
    }

    private enum Direction {
        LEFT,
        RIGHT,
        DOWN,
        UP
    }

    private enum OutputState {
        PAINT,
        DIRECTION
    }

    private static class Point {
        private final int x;
        private final int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x &&
                   y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
