package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        List<Integer> numbers = parseInput(input);

        return countIncreasingNumbers(numbers, 0);
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> numbers = parseInput(input);

        return countIncreasingNumbers(numbers, 2);
    }

    private int countIncreasingNumbers(List<Integer> numbers, int offset) {
        int previous = numbers.get(0);

        int amountIncreasing = 0;

        for (int i = 1; i < numbers.size() - offset; i++) {
            int number = numbers.get(i + offset);

            if (number > previous) {
                amountIncreasing++;
            }

            previous = numbers.get(i);
        }
        return amountIncreasing;
    }

    private List<Integer> parseInput(Stream<String> input) {
        return input.map(Integer::parseInt)
                    .toList();
    }
}
