package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::mapToPriority)
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var sum = 0;

        for (var i = 0; i < lines.size(); i += 3) {
            var line1 = lines.get(i);
            var line2 = lines.get(i + 1);
            var line3 = lines.get(i + 2);

            for (var c : line1.toCharArray()) {
                if (line2.contains(String.valueOf(c)) && line3.contains(String.valueOf(c))) {
                    sum += getPriority(c);
                    break;
                }
            }
        }

        return sum;
    }

    private int mapToPriority(String line) {
        var compartment1 = line.substring(0, line.length() / 2);
        var compartment2 = line.substring(line.length() / 2);

        for (var c : compartment1.toCharArray()) {
            if (compartment2.contains(String.valueOf(c))) {
                return getPriority(c);
            }
        }

        throw new AssertionError("Eek!");
    }

    private static int getPriority(char c) {
        if (Character.isLowerCase(c)) {
            return c - ('a' - 1);
        } else {
            return c - ('A' - 27);
        }
    }
}
