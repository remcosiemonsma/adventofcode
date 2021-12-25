package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day25 {
    public int handlePart1(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        boolean movePossible = true;
        int steps = 0;

        int maxy = grid.length;
        int maxx = grid[0].length;

        while (movePossible) {
            movePossible = false;

            char[][] newGrid = new char[grid.length][grid[0].length];

            for (int y = 0; y < grid.length; y++) {
                newGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
            }

            for (int y = 0; y < maxy; y++) {
                for (int x = 0; x < maxx; x++) {
                    if (grid[y][x] == '>' && grid[y][(x + 1) % maxx] == '.') {
                        newGrid[y][x] = '.';
                        newGrid[y][(x + 1) % maxx] = '>';
                        movePossible = true;
                    }
                }
            }

            grid = newGrid;

            newGrid = new char[grid.length][grid[0].length];

            for (int y = 0; y < grid.length; y++) {
                newGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
            }

            for (int x = 0; x < maxx; x++) {
                for (int y = 0; y < maxy; y++) {
                    if (grid[y][x] == 'v' && grid[(y + 1) % maxy][x] == '.') {
                        newGrid[y][x] = '.';
                        newGrid[(y + 1) % maxy][x] = 'v';
                        movePossible = true;
                        y++;
                    }
                }
            }

            grid = newGrid;

            steps++;
        }

        return steps;
    }
}
