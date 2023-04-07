package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var grid = input.map(String::toCharArray)
                        .toArray(char[][]::new);

        return getAmountOfTrees(grid, 1, 3);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var grid = input.map(String::toCharArray)
                        .toArray(char[][]::new);

        var firstAmount = getAmountOfTrees(grid, 1, 1);
        var secondAmount = getAmountOfTrees(grid, 1, 3);
        var thirdAmount = getAmountOfTrees(grid, 1, 5);
        var fourthAmount = getAmountOfTrees(grid, 1, 7);
        var fifthAmount = getAmountOfTrees(grid, 2, 1);

        return firstAmount * secondAmount * thirdAmount * fourthAmount * fifthAmount;
    }

    private long getAmountOfTrees(char[][] grid, int amountDown, int amountRight) {
        var column = 0;

        var amountOfTrees = 0;

        for (var row = 0; row < grid.length; row += amountDown) {
            if (grid[row][column] == '#') {
                amountOfTrees++;
            }

            column += amountRight;

            if (column >= grid[row].length) {
                column -= grid[row].length;
            }
        }
        return amountOfTrees;
    }
}
