package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.intcodecomputer.ConsumingQueue;
import nl.remcoder.adventofcode.intcodecomputer.IntCodeComputer;
import nl.remcoder.adventofcode.intcodecomputer.ProducingQueue;
import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;
import nl.remcoder.adventofcode.library.drawing.Screen;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class Day11 implements BiAdventOfCodeSolution<Integer, String> {
    private Coordinate position;
    private Direction direction;
    private Grid<Boolean> grid;
    private Set<Coordinate> paintedPoints;
    private OutputState outputState = OutputState.PAINT;

    @Override
    public Integer handlePart1(Stream<String> inputStream) {
        var line = inputStream.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var output = new ConsumingQueue(this::handleOutput);
        var input = new ProducingQueue(this::provideInput);

        grid = new Grid<>(0, 0, 96, 96);

        position = new Coordinate(48, 48);

        direction = Direction.UP;

        paintedPoints = new HashSet<>();

        var robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        return paintedPoints.size();
    }

    @Override
    public String handlePart2(Stream<String> inputStream) {
        var line = inputStream.findFirst().orElseThrow(AssertionError::new);

        var opcodes = Arrays.stream(line.split(","))
                            .mapToLong(Long::parseLong)
                            .toArray();

        var output = new ConsumingQueue(this::handleOutput);
        var input = new ProducingQueue(this::provideInput);

        grid = new Grid<>(0, 0, 96, 96);

        position = new Coordinate(48, 48);

        direction = Direction.UP;

        grid.set(position, true);

        paintedPoints = new HashSet<>();

        var robotBrain = new IntCodeComputer(opcodes, input, output);

        robotBrain.runProgram();

        var screen = new Screen(grid);

        return screen.readScreen();
    }

    private long provideInput() {
        return Boolean.TRUE.equals(grid.get(position)) ? 1L : 0L;
    }

    private void handleOutput(Long outputValue) {
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
    }

    private void rotate(Long direction) {
        if (direction == 0) {
            rotateLeft();
        } else if (direction == 1) {
            rotateRight();
        }
    }

    private void paint(Long color) {
        paintedPoints.add(position);
        if (color == 0) {
            grid.set(position, false);
        } else if (color == 1) {
            grid.set(position, true);
        }
    }

    private void move() {
        position = switch (direction) {
            case UP -> position.above();
            case LEFT -> position.left();
            case DOWN -> position.below();
            case RIGHT -> position.right();
        };
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

    private enum OutputState {
        PAINT,
        DIRECTION
    }
}
