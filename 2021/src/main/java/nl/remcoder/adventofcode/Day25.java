package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day25 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        var movePossible = true;
        var steps = 0;

        var maxy = grid.length;
        var maxx = grid[0].length;

        while (movePossible) {
            movePossible = false;

            var newGrid = new char[grid.length][grid[0].length];

            for (var y = 0; y < grid.length; y++) {
                newGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
            }

            for (var y = 0; y < maxy; y++) {
                for (var x = 0; x < maxx; x++) {
                    if (grid[y][x] == '>' && grid[y][(x + 1) % maxx] == '.') {
                        newGrid[y][x] = '.';
                        newGrid[y][(x + 1) % maxx] = '>';
                        movePossible = true;
                    }
                }
            }

            grid = newGrid;

            newGrid = new char[grid.length][grid[0].length];

            for (var y = 0; y < grid.length; y++) {
                newGrid[y] = Arrays.copyOf(grid[y], grid[y].length);
            }

            for (var x = 0; x < maxx; x++) {
                for (var y = 0; y < maxy; y++) {
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

    @Override
    public Integer handlePart2(Stream<String> input) throws Exception {
        System.out.println("Merry Christmas!");
        return null;
    }
}
