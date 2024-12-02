package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day2 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.map(s -> s.split(" "))
                    .map(this::mapToInts)
                    .filter(this::isValid)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.map(s -> s.split(" "))
                    .map(this::mapToInts)
                    .filter(this::isValidPart2)
                    .count();
    }

    private int[] mapToInts(String[] numbers) {
        int[] ints = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            ints[i] = Integer.parseInt(numbers[i]);
        }
        return ints;
    }

    private boolean isValid(int[] values) {
        var first = values[0];
        var previous = values[1];
        var descending = first > previous;

        if (isInvalidDifference(first, previous)) {
            return false;
        }

        for (int i = 2; i < values.length; i++) {
            var current = values[i];

            if (descending) {
                if (current > previous) {
                    return false;
                }
            } else {
                if (current < previous) {
                    return false;
                }
            }
            if (isInvalidDifference(current, previous)) {
                return false;
            }
            previous = current;
        }
        return true;
    }

    private boolean isValidPart2(int[] values) {
        if (isValid(values)) {
            return true;
        } else {
            for (int i = 0; i < values.length; i++) {
                var newValues = remove(values, i);
                if (isValid(newValues)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInvalidDifference(int first, int previous) {
        int difference = Math.abs(first - previous);
        return difference < 1 || difference > 3;
    }

    private int[] remove(int[] values, int removeIndex) {
        int[] newValues = new int[values.length - 1];
        System.arraycopy(values, 0, newValues, 0, removeIndex);
        System.arraycopy(values, removeIndex + 1, newValues, removeIndex, values.length - removeIndex - 1);
        return newValues;
    }
}
