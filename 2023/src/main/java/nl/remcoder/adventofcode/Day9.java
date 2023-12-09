package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day9 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.map(this::parseNumbers)
                    .mapToInt(this::determineNextNumber)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.map(this::parseNumbers)
                    .mapToInt(this::determinePreviousNumber)
                    .sum();
    }

    private int determineNextNumber(List<Integer> values) {
        var differences = new ArrayList<Integer>();

        for (int i = 0; i < values.size() - 1; i++) {
            differences.add(values.get(i + 1) - values.get(i));
        }

        if (differences.stream().allMatch(difference -> difference == 0)) {
            return values.getLast();
        } else {
            return values.getLast() + determineNextNumber(differences);
        }
    }

    private int determinePreviousNumber(List<Integer> values) {
        var differences = new ArrayList<Integer>();

        for (int i = 0; i < values.size() - 1; i++) {
            differences.add(values.get(i + 1) - values.get(i));
        }

        if (differences.stream().allMatch(difference -> difference == 0)) {
            return values.getFirst();
        } else {
            return values.getFirst() - determinePreviousNumber(differences);
        }
    }

    private List<Integer> parseNumbers(String line) {
        return Arrays.stream(line.split(" "))
                     .map(Integer::parseInt)
                     .toList();
    }
}
