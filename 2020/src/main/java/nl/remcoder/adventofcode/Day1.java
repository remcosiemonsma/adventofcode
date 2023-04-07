package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var sum = 2020;

        var entries = input.map(Integer::parseInt)
                           .toList();

        for (var entry : entries) {
            var difference = sum - entry;

            if (entries.contains(difference)) {
                return difference * entry;
            }
        }

        return 0;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var entries = input.map(Integer::parseInt)
                           .sorted()
                           .toList();

        var entriesSize = entries.size();

        for (var first = 0; first < entriesSize; first++) {
            for (var second = first + 1; second < entriesSize; second++) {
                for (var third = second + 1; third < entriesSize; third++) {
                    if (entries.get(first) + entries.get(second) + entries.get(third) == 2020) {
                        return entries.get(first) * entries.get(second) * entries.get(third);
                    }
                }
            }
        }

        return 0;
    }
}
