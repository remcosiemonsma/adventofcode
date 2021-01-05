package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 {
    public int handlePart1(Stream<String> input) {
        return input.map(line -> line.split("x"))
                    .map(split -> new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                                                Integer.parseInt(split[2])))
                    .map(dimension -> new Sides(dimension.length * dimension.width, dimension.length * dimension.height,
                                                dimension.width * dimension.height))
                    .mapToInt(sides -> {
                        int extra = Math.min(sides.side1, Math.min(sides.side2, sides.side3));
                        return (2 * sides.side1) + (2 * sides.side2) + (2 * sides.side3) + extra;
                    })
                    .sum();
    }

    public int handlePart2(Stream<String> input) {
        return input.map(line -> line.split("x"))
                    .map(split -> new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                                                Integer.parseInt(split[2])))
                    .map(dimension -> new int[]{dimension.length, dimension.width, dimension.height})
                    .peek(Arrays::sort)
                    .mapToInt(sides -> (sides[0] * 2) + (sides[1] * 2) + (sides[0] * sides[1] * sides[2]))
                    .sum();
    }

    private static class Sides {
        final int side1;
        final int side2;
        final int side3;

        private Sides(int side1, int side2, int side3) {
            this.side1 = side1;
            this.side2 = side2;
            this.side3 = side3;
        }
    }

    private static class Dimension {
        final int length;
        final int width;
        final int height;

        private Dimension(int length, int width, int height) {
            this.length = length;
            this.width = width;
            this.height = height;
        }
    }
}
