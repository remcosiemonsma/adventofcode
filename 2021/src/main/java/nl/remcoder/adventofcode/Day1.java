package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        List<Integer> numbers = input.map(Integer::parseInt)
                                     .collect(Collectors.toList());

        int previous = numbers.get(0);

        int amountIncreasing = 0;

        for (int i = 1; i < numbers.size(); i++) {
            int number = numbers.get(i);

            if (number > previous) {
                amountIncreasing++;
            }

            previous = number;
        }

        return amountIncreasing;
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> numbers = input.map(Integer::parseInt)
                                     .collect(Collectors.toList());

        int previous = numbers.get(0) + numbers.get(1) + numbers.get(2);

        int amountIncreasing = 0;

        for (int i = 1; i < numbers.size() - 2; i++) {
            int number = numbers.get(i) + numbers.get(i + 1) + numbers.get(i + 2);

            if (number > previous) {
                amountIncreasing++;
            }

            previous = number;
        }

        return amountIncreasing;
    }
}
