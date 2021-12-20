package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Stream;

public class Day20 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.toList();

        String first = lines.get(0);

        boolean[] enhancement = createImageLine(first);

        boolean[][] grid = lines.stream()
                                .skip(2)
                                .map(this::createImageLine)
                                .toArray(boolean[][]::new);

        grid = growGrid(grid);
        grid = growGrid(grid);

        for (int iteration = 0; iteration < 2; iteration++) {
            grid = enhance(grid, enhancement, iteration % 2 == 0);
        }

        return countPixels(grid);
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.toList();

        String first = lines.get(0);

        boolean[] enhancement = createImageLine(first);

        boolean[][] grid = lines.stream()
                                .skip(2)
                                .map(this::createImageLine)
                                .toArray(boolean[][]::new);

        for (int i = 0; i < 50; i ++) {
            grid = growGrid(grid);
        }

        for (int iteration = 0; iteration < 50; iteration++) {
            grid = enhance(grid, enhancement, iteration % 2 == 0);
        }

        return countPixels(grid);
    }

    private boolean[] createImageLine(String line) {
        boolean[] imageline = new boolean[line.length()];

        for (int i = 0; i < line.length(); i++) {
            imageline[i] = line.charAt(i) == '#';
        }

        return imageline;
    }

    private boolean[][] enhance(boolean[][] grid, boolean[] enhancement, boolean isEvenOperation) {
        boolean[][] newGrid = new boolean[grid.length][grid[0].length];

        for (int y = 0; y < newGrid.length; y++) {
            for (int x = 0; x < newGrid[y].length; x++) {
                newGrid[y][x] = getNewValue(grid, enhancement, x, y, isEvenOperation);
            }
        }

        return newGrid;
    }

    private boolean getNewValue(boolean[][] grid, boolean[] enhancement, int x, int y, boolean isEvenOperation) {
        StringBuilder numberBuilder = new StringBuilder();

        for (int yoffset = -1; yoffset <= 1; yoffset++) {
            for (int xoffset = -1; xoffset <= 1; xoffset++) {
                int ypos = y + yoffset;
                int xpos = x + xoffset;

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

    private boolean[][] growGrid(boolean[][] grid) {
        boolean[][] newGrid = new boolean[grid.length + 2][grid[0].length + 2];

        for (int y = 0; y < grid.length; y++) {
            System.arraycopy(grid[y], 0, newGrid[y + 1], 1, grid[y].length);
        }

        return newGrid;
    }

    private int countPixels(boolean[][] grid) {
        int count = 0;

        for (boolean[] line : grid) {
            for (boolean pixel : line) {
                if (pixel) {
                    count++;
                }
            }
        }

        return count;
    }
}
