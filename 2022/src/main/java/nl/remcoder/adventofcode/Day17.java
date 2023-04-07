package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.util.*;
import java.util.stream.Stream;

public class Day17 implements AdventOfCodeSolution<Long> {
    private static final boolean[][] BAR_BLOCK = new boolean[][] {{true, true, true, true}};
    private static final boolean[][] CROSS_BLOCK = new boolean[][] {{false, true, false},
                                                                    {true, true, true},
                                                                    {false, true, false}};
    private static final boolean[][] L_BLOCK = new boolean[][] {{false, false, true},
                                                                {false, false, true},
                                                                {true, true, true}};
    private static final boolean[][] I_BLOCK = new boolean[][] {{true},
                                                                {true},
                                                                {true},
                                                                {true}};
    private static final boolean[][] SQUARE_BLOCK = new boolean[][] {{true, true},
                                                                     {true, true}};
    private final Queue<boolean[][]> BLOCK_QUEUE = new ArrayDeque<>();

    private void initializeQueue() {
        BLOCK_QUEUE.clear();
        BLOCK_QUEUE.add(BAR_BLOCK);
        BLOCK_QUEUE.add(CROSS_BLOCK);
        BLOCK_QUEUE.add(L_BLOCK);
        BLOCK_QUEUE.add(I_BLOCK);
        BLOCK_QUEUE.add(SQUARE_BLOCK);
    }

    @Override
    public Long handlePart1(Stream<String> input) {
        initializeQueue();
        var movementQueue = new ArrayDeque<>(input.flatMap(s -> s.chars()
                                                                 .mapToObj(value -> (char) value))
                                                  .toList());

        var grid = new Grid<Boolean>(0, 0, 6, 4000);

        var currentHeight = 0;

        for (var i = 0; i < 2022; i++) {
            var block = BLOCK_QUEUE.remove();
            BLOCK_QUEUE.add(block);

            var shapePosition = new Coordinate(2, 4000 - (currentHeight + 3 + block.length - 1));

            var shapeCanFall = true;

            var shape = new Shape(block, shapePosition);

            while (shapeCanFall) {
                var movement = movementQueue.remove();
                movementQueue.add(movement);

                shape.move(movement, grid);
                shapeCanFall = shape.moveDown(grid);
            }

            var shapeHeight = 4001 - shape.position.y();

            if (shapeHeight > currentHeight) {
                currentHeight = shapeHeight;
            }

            shape.drawShape(grid);
        }

        return (long) currentHeight;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        initializeQueue();
        var movementQueue = new ArrayDeque<>(input.flatMap(s -> s.chars()
                                                                 .mapToObj(value -> (char) value))
                                                  .toList());

        List<State> states = new ArrayList<>();

        boolean cycleFound = false;

        var grid = new Grid<Boolean>(0, 0, 6, 4000);

        var currentHeight = 0;
        
        while (!cycleFound) {
            var blocks = new ArrayList<>(BLOCK_QUEUE);
            var movements = new ArrayList<>(movementQueue);
            var heightGrowth = 0;

            var block = BLOCK_QUEUE.remove();
            BLOCK_QUEUE.add(block);

            var shapePosition = new Coordinate(2, 4000 - (currentHeight + 3 + block.length - 1));

            var shapeCanFall = true;

            var shape = new Shape(block, shapePosition);

            while (shapeCanFall) {
                var movement = movementQueue.remove();
                movementQueue.add(movement);

                shape.move(movement, grid);
                shapeCanFall = shape.moveDown(grid);
            }

            var shapeHeight = 4001 - shape.position.y();

            if (shapeHeight > currentHeight) {
                heightGrowth = shapeHeight - currentHeight;
                currentHeight = shapeHeight;
            }

            shape.drawShape(grid);

            State state = new State(blocks, movements, determineHeightOffset(grid, currentHeight), heightGrowth);

            if (states.contains(state)) {
                cycleFound = true;
            }
            states.add(state);
        }

        var lastState = states.get(states.size() - 1);

        states = states.subList(0, states.size() - 1);

        var cycleStartIndex = states.indexOf(lastState);

        var cycleSize = states.size() - cycleStartIndex;

        var totalCycles = (1000000000000L - (cycleStartIndex)) / cycleSize;

        var remainingCycles = (1000000000000L - (cycleStartIndex)) % cycleSize;
        
        var growthInCycle = states.stream()
                                  .skip(cycleStartIndex)
                                  .mapToInt(State::heightGrow)
                                  .sum();

        var growthBeforeCycle = states.stream()
                                      .limit(cycleStartIndex)
                                      .mapToInt(State::heightGrow)
                                      .sum();

        var growthDuringCycles = totalCycles * growthInCycle;

        var growthInRemainingCycles = states.stream()
                                            .skip(cycleStartIndex)
                                            .limit(remainingCycles)
                                            .mapToInt(State::heightGrow)
                                            .sum();

        return growthBeforeCycle + growthDuringCycles + growthInRemainingCycles;
    }

    private int[] determineHeightOffset(Grid<Boolean> grid, int currentHeight) {
        int[] offset = new int[6];

        int heightInFirstRow = determineHeightInRow(grid, 0, currentHeight);

        for (int x = 1; x < 7; x++) {
            int heightInRow = determineHeightInRow(grid, x, currentHeight);
            offset[x - 1] = heightInFirstRow - heightInRow;
        }
        return offset;
    }
    
    private int determineHeightInRow(Grid<Boolean> grid, int row, int currentHeight) {
        while (currentHeight >= 0) {
            if (Boolean.TRUE.equals(grid.get(new Coordinate(row, currentHeight)))) {
                return currentHeight;
            }
            currentHeight--;
        }
        return 0;
    }
    
    private record State(List<boolean[][]> blocks, List<Character> movement, int[] heightOffset, int heightGrow) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            State state = (State) o;
            return Objects.equals(blocks, state.blocks) && Objects.equals(movement, state.movement) &&
                   Arrays.equals(heightOffset, state.heightOffset);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(blocks, movement);
            result = 31 * result + Arrays.hashCode(heightOffset);
            return result;
        }
    }

    private static class Shape {
        private final boolean[][] blocks;
        private Coordinate position;

        private Shape(boolean[][] blocks, Coordinate position) {
            this.blocks = blocks;
            this.position = position;
        }

        public void move(char direction, Grid<Boolean> grid) {
            switch (direction) {
                case '>' -> moveright(grid);
                case '<' -> moveLeft(grid);
            }
        }

        public void drawShape(Grid<Boolean> grid) {
            for (int y = 0; y < blocks.length; y++) {
                for (int x = 0; x < blocks[y].length; x++) {
                    if (blocks[y][x]) {
                        Coordinate pixelCoordinate = new Coordinate(position.x() + x, position.y() + y);
                        grid.set(pixelCoordinate, true);
                    }
                }
            }
        }

        private void moveright(Grid<Boolean> grid) {
            Coordinate newPosition = position.right();

            if (grid.isCoordinateInGrid(new Coordinate(newPosition.x() + blocks[0].length - 1, newPosition.y()))) {
                //detect collision
                if (collision(grid, newPosition)) {
                    return;
                }
                position = newPosition;
            }
        }

        private void moveLeft(Grid<Boolean> grid) {
            Coordinate newPosition = position.left();

            if (grid.isCoordinateInGrid(newPosition)) {
                //detect collision
                if (collision(grid, newPosition)) {
                    return;
                }
                position = newPosition;
            }
        }

        private boolean moveDown(Grid<Boolean> grid) {
            Coordinate newPosition = position.below();

            if (grid.isCoordinateInGrid(new Coordinate(newPosition.x(), newPosition.y() + blocks.length - 1))) {
                if (collision(grid, newPosition)) {
                    return false;
                }
                position = newPosition;
                return true;
            } else {
                return false;
            }
        }

        private boolean collision(Grid<Boolean> grid, Coordinate newPosition) {
            for (int y = 0; y < blocks.length; y++) {
                for (int x = 0; x < blocks[y].length; x++) {
                    if (blocks[y][x]) {
                        Coordinate pixelCoordinate = new Coordinate(newPosition.x() + x, newPosition.y() + y);

                        if (Boolean.TRUE.equals(grid.get(pixelCoordinate))) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }
}
