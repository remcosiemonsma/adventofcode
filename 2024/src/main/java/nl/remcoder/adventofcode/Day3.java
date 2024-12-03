package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d*),(\\d*)\\)");
    private static final Pattern MUL_PATTERN_2 = Pattern.compile("mul\\((\\d*),(\\d*)\\)|don't\\(\\)|do\\(\\)");
    private boolean enabled = true;

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateLineResult)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.mapToInt(this::calculateLineResult2)
                    .sum();
    }

    private int calculateLineResult(String line) {
        Matcher matcher = MUL_PATTERN.matcher(line);

        var result = 0;

        while (matcher.find()) {
            result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
        }

        return result;
    }

    private int calculateLineResult2(String line) {
        Matcher matcher = MUL_PATTERN_2.matcher(line);

        var result = 0;

        while (matcher.find()) {
            if (matcher.group(0).equals("don't()")) {
                enabled = false;
            } else if (matcher.group(0).equals("do()")) {
                enabled = true;
            } else if (enabled) {
                result += Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
            }
        }

        return result;
    }
}
