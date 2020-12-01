package nl.remcoder.adventofcode;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        int sum = 2020;

        List<Integer> entries = input.map(Integer::parseInt)
                                     .collect(Collectors.toList());

        for (int entry : entries) {
            int difference = sum - entry;

            if (entries.contains(difference)) {
                return difference * entry;
            }
        }

        return 0;
    }

    public int handlePart2(Stream<String> input) {
        List<Integer> entries = input.map(Integer::parseInt)
                                     .sorted()
                                     .collect(Collectors.toList());

        int entriesSize = entries.size();

        for (int first = 0; first < entriesSize; first++) {
            for (int second = first + 1; second < entriesSize; second++) {
                for (int third = second + 1; third < entriesSize; third++) {
                    if (entries.get(first) + entries.get(second) + entries.get(third) == 2020) {
                        return entries.get(first) * entries.get(second) * entries.get(third);
                    }
                }
            }
        }

        return 0;
    }
}
