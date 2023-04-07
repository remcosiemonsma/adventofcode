package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var first = lines.get(0);

        var enhancement = createImageLine(first);

        var grid = lines.stream()
                        .skip(2)
                        .map(this::createImageLine)
                        .toArray(boolean[][]::new);

        grid = growGrid(grid, 2);

        for (var iteration = 0; iteration < 2; iteration++) {
            grid = enhance(grid, enhancement, iteration % 2 == 0);
        }

        return countPixels(grid);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var first = lines.get(0);

        var enhancement = createImageLine(first);

        var grid = lines.stream()
                        .skip(2)
                        .map(this::createImageLine)
                        .toArray(boolean[][]::new);

        grid = growGrid(grid, 50);

        for (var iteration = 0; iteration < 50; iteration++) {
            grid = enhance(grid, enhancement, iteration % 2 == 0);
        }

        return countPixels(grid);
    }

    private boolean[] createImageLine(String line) {
        var imageline = new boolean[line.length()];

        for (var i = 0; i < line.length(); i++) {
            imageline[i] = line.charAt(i) == '#';
        }

        return imageline;
    }

    private boolean[][] enhance(boolean[][] grid, boolean[] enhancement, boolean isEvenOperation) {
        var newGrid = new boolean[grid.length][grid[0].length];

        for (var y = 0; y < newGrid.length; y++) {
            for (var x = 0; x < newGrid[y].length; x++) {
                newGrid[y][x] = getNewValue(grid, enhancement, x, y, isEvenOperation);
            }
        }

        return newGrid;
    }

    private boolean getNewValue(boolean[][] grid, boolean[] enhancement, int x, int y, boolean isEvenOperation) {
        var numberBuilder = new StringBuilder();

        for (var yoffset = -1; yoffset <= 1; yoffset++) {
            for (var xoffset = -1; xoffset <= 1; xoffset++) {
                var ypos = y + yoffset;
                var xpos = x + xoffset;

                if (isPositionInvalid(grid, ypos, xpos)) {
                    if (isEvenOperation) {
                        numberBuilder.append('0');
                    } else {
                        if (grid[0][0]) {
                            numberBuilder.append('1');
                        } else {
                            numberBuilder.append('0');
                        }
                    }
                } else {
                    if (grid[ypos][xpos]) {
                        numberBuilder.append('1');
                    } else {
                        numberBuilder.append('0');
                    }
                }
            }
        }

        int position = Integer.parseInt(numberBuilder.toString(), 2);

        return enhancement[position];
    }

    private boolean isPositionInvalid(boolean[][] grid, int ypos, int xpos) {
        return ypos < 0 || ypos > grid.length - 1 || xpos < 0 || xpos > grid[ypos].length - 1;
    }

    private boolean[][] growGrid(boolean[][] grid, int times) {
        var newGrid = new boolean[grid.length + (times * 2)][grid[0].length + (times * 2)];

        for (var y = 0; y < grid.length; y++) {
            System.arraycopy(grid[y], 0, newGrid[y + times], times, grid[y].length);
        }

        return newGrid;
    }

    private int countPixels(boolean[][] grid) {
        var count = 0;

        for (var line : grid) {
            for (var pixel : line) {
                if (pixel) {
                    count++;
                }
            }
        }

        return count;
    }
}
