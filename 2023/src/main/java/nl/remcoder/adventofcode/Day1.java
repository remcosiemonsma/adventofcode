package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    private final Pattern NUMBERS =
            Pattern.compile("(1|2|3|4|5|6|7|8|9|one|two|three|four|five|six|seven|eight|nine)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::getCalibrationValue)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(NUMBERS::matcher)
                    .mapToInt(this::getCalibrationValue2)
                    .sum();
    }

    private int getCalibrationValue2(Matcher matcher) {
        var numbers = new ArrayList<String>();

        var position = 0;

        while (matcher.find(position)) {
            numbers.add(matcher.group(1));
            position = matcher.start() + 1;
        }

        var firstDigit = stringToNumber(numbers.getFirst());
        var secondDigit = stringToNumber(numbers.getLast());

        return firstDigit * 10 + secondDigit;
    }

    private int stringToNumber(String s) {
        return switch (s) {
            case "1", "one" -> 1;
            case "2", "two" -> 2;
            case "3", "three" -> 3;
            case "4", "four" -> 4;
            case "5", "five" -> 5;
            case "6", "six" -> 6;
            case "7", "seven" -> 7;
            case "8", "eight" -> 8;
            case "9", "nine" -> 9;
            default -> throw new AssertionError("Eek!");
        };
    }

    private int getCalibrationValue(String s) {
        var firstDigit = findFirstDigit(s);
        var secondDigit = findLastDigit(s);

        return firstDigit * 10 + secondDigit;
    }

    private int findFirstDigit(String s) {
        var position = 0;

        while (position < s.length()) {
            if (Character.isDigit(s.charAt(position))) {
                return s.charAt(position) - '0';
            }
            position++;
        }

        throw new AssertionError("Eek!");
    }

    private int findLastDigit(String s) {
        var position = s.length() - 1;

        while (position >= 0) {
            if (Character.isDigit(s.charAt(position))) {
                return s.charAt(position) - '0';
            }
            position--;
        }

        throw new AssertionError("Eek!");
    }
}
