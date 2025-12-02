package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Long> {
    private final static String REPEATING_PATTERN_TWICE = "^(\\d+)\\1$";
    private final static String REPEATING_PATTERN_MULTIPLE = "^(\\d+)\\1+$";

    @Override
    public Long handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var ranges = line.split(",");

        var result = 0L;

        for (var range : ranges) {
            var edges = range.split("-");

            var start = Long.parseLong(edges[0]);
            var end = Long.parseLong(edges[1]);

            result += LongStream.range(start, end + 1)
                                .filter(this::isRepeatingTwice)
                                .sum();
        }

        return result;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var ranges = line.split(",");

        var result = 0L;

        for (var range : ranges) {
            var edges = range.split("-");

            var start = Long.parseLong(edges[0]);
            var end = Long.parseLong(edges[1]);

            result += LongStream.range(start, end + 1)
                                .filter(this::isRepeatingMultiple)
                                .sum();
        }

        return result;
    }

    private boolean isRepeatingTwice(long i) {
        return Long.toString(i).matches(REPEATING_PATTERN_TWICE);
    }

    private boolean isRepeatingMultiple(long i) {
        return Long.toString(i).matches(REPEATING_PATTERN_MULTIPLE);
    }
}
