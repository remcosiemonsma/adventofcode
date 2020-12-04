package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        return getAmountOfTrees(grid, 1, 3);
    }

    public long handlePart2(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        long firstAmount = getAmountOfTrees(grid, 1, 1);
        long secondAmount = getAmountOfTrees(grid, 1, 3);
        long thirdAmount = getAmountOfTrees(grid, 1, 5);
        long fourthAmount = getAmountOfTrees(grid, 1, 7);
        long fifthAmount = getAmountOfTrees(grid, 2, 1);

        return firstAmount * secondAmount * thirdAmount * fourthAmount * fifthAmount;
    }

    private int getAmountOfTrees(char[][] grid, int amountDown, int amountRight) {
        int column = 0;

        int amountOfTrees = 0;

        for (int row = 0; row < grid.length; row += amountDown) {
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
