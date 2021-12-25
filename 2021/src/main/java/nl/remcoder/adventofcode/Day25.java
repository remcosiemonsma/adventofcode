package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day25 {
    public int handlePart1(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        boolean movePossible = true;
        int steps = 0;

        while (movePossible) {
            char[][] newGrid = new char[grid.length][grid[0].length];

            for (int i = 0; i < grid.length; i++) {
                newGrid[i] = Arrays.copyOf(grid[i], grid[i].length);
            }

            movePossible = false;

            printGrid(newGrid);

            for (int y = 0; y < grid.length; y++) {
                if (grid[y][grid[y].length - 1] == '>' && grid[y][0] == '.') {
                    newGrid[y][0] = '>';
                    newGrid[y][grid[y].length - 1] = '.';
                    movePossible = true;
                }

                for (int x = grid[y].length - 2; x >= 0; x--) {
                    if (grid[y][x] == '>' && grid[y][x + 1] == '.') {
                        newGrid[y][x + 1] = '>';
                        newGrid[y][x] = '.';
                        movePossible = true;
                    }
                }
            }

            for (int y = grid.length - 1; y >= 0; y--) {
                for (int x = 0; x < grid[y].length; x++) {
                    if (grid[y][x] == 'v') {
                        if (y == grid.length - 1) {
                            if (grid[0][x] == '.') {
                                newGrid[0][x] = 'v';
                                newGrid[y][x] = '.';
                                movePossible = true;
                            }
                        } else {
                            if (grid[y + 1][x] == '.') {
                                newGrid[y + 1][x] = 'v';
                                newGrid[y][x] = '.';
                                movePossible = true;
                            }
                        }
                    }
                }
            }

            grid = newGrid;

            steps++;
        }

        return steps - 1;
    }

    public int handlePart2(Stream<String> input) {
        return 0;
    }

    public void printGrid(char[][] grid) {
        for (char[] line : grid) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
}
