package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.stream.Stream;

public class Day3 implements BiAdventOfCodeSolution<Integer, Long> {

    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.mapToInt(this::calculateLargestJoltage)
                    .sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.mapToLong(this::calculateLargestJoltagePart2)
                    .sum();
    }

    private int calculateLargestJoltage(String line) {
        var largestFirstJoltage = 0;
        var joltageIndex = 0;
        for (var i = 0; i < line.length() - 1; i++) {
            var joltage = line.charAt(i) - '0';
            if  (joltage > largestFirstJoltage) {
                largestFirstJoltage = joltage;
                joltageIndex = i;
            }
        }

        var largestSecondJoltage = 0;
        for (var i = line.length() - 1; i > joltageIndex; i--) {
            var joltage = line.charAt(i) - '0';
            if  (joltage > largestSecondJoltage) {
                largestSecondJoltage = joltage;
            }
        }

        return (largestFirstJoltage * 10) + largestSecondJoltage;
    }

    private long calculateLargestJoltagePart2(String line) {
        var totalJoltage = 0L;
        var largestIndex = -1;

        for (var i = 11; i >= 0; i--) {
            var largestJoltage = 0;
            for (var j = largestIndex + 1; j < line.length() - i; j++) {
                var joltage = line.charAt(j) - '0';
                if  (joltage > largestJoltage) {
                    largestJoltage = joltage;
                    largestIndex = j;
                }
            }
            totalJoltage += (long) (largestJoltage * Math.pow(10, i));
        }

        return totalJoltage;
    }
}