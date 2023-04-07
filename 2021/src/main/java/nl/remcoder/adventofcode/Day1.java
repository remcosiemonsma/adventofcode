package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.List;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var numbers = input.map(Integer::parseInt)
                           .toList();

        return countIncreasingNumbers(numbers, 0);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var numbers = input.map(Integer::parseInt)
                           .toList();

        return countIncreasingNumbers(numbers, 2);
    }

    private int countIncreasingNumbers(List<Integer> numbers, int offset) {
        var previous = numbers.get(0);

        var amountIncreasing = 0;

        for (var i = 1; i < numbers.size() - offset; i++) {
            var number = numbers.get(i + offset);

            if (number > previous) {
                amountIncreasing++;
            }

            previous = numbers.get(i);
        }
        return amountIncreasing;
    }
}
