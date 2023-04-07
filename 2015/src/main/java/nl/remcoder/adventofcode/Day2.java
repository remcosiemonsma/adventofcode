package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(line -> line.split("x"))
                    .map(this::mapToDimension)
                    .map(this::mapToSides)
                    .mapToInt(this::calculateSquareFootage)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(line -> line.split("x"))
                    .map(this::mapToDimension)
                    .map(this::mapToLengths)
                    .peek(Arrays::sort)
                    .mapToInt(this::calculateRibbonLength)
                    .sum();
    }

    private int calculateRibbonLength(int[] sides) {
        return (sides[0] * 2) + (sides[1] * 2) + (sides[0] * sides[1] * sides[2]);
    }

    private int[] mapToLengths(Dimension dimension) {
        return new int[]{dimension.length(), dimension.width(), dimension.height()};
    }

    private int calculateSquareFootage(Sides sides) {
        var extra = Math.min(sides.side1(), Math.min(sides.side2(), sides.side3()));
        return (2 * sides.side1()) + (2 * sides.side2()) + (2 * sides.side3()) + extra;
    }

    private Sides mapToSides(Dimension dimension) {
        return new Sides(dimension.length() * dimension.width(), dimension.length() * dimension.height(),
                         dimension.width() * dimension.height());
    }

    private Dimension mapToDimension(String[] split) {
        return new Dimension(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
                             Integer.parseInt(split[2]));
    }

    private record Sides(int side1, int side2, int side3) {}

    private record Dimension(int length, int width, int height) {}
}
