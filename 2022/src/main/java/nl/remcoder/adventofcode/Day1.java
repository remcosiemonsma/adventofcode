package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.stream.CombiningCollector;

import java.util.*;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        return input.collect(new CombiningCollector<>(Integer::parseInt, String::isBlank))
                    .mapToInt(bag -> bag.stream()
                                        .mapToInt(Integer::intValue)
                                        .sum())
                    .max()
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    public int handlePart2(Stream<String> input) {
        return input.collect(new CombiningCollector<>(Integer::parseInt, String::isBlank))
                    .map(bag -> bag.stream()
                                   .mapToInt(Integer::intValue)
                                   .sum())
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .mapToInt(Integer::intValue)
                    .sum();
    }

}