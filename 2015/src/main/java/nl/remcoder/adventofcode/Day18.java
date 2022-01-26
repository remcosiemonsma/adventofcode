package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.GridFactory;

import java.util.stream.Stream;

public class Day18 {
    public int handlePart1(Stream<String> input) {
        boolean[][] grid = GridFactory.createBooleanGridFromInput(input);

        GridFactory.printGrid(grid);

        return 0;
    }

    public int handlePart2(Stream<String> input) {
        return 0;
    }
}
