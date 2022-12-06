package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.stream.CountingCollector;

import java.util.Map;
import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        for (var i = 0; i < line.length() - 4; i++) {
            var window = line.substring(i, i + 4);

            Map<Character, Integer> count = window.chars()
                                                  .mapToObj(value -> (char) value)
                                                  .collect(new CountingCollector<>());

            if (count.values().stream().allMatch(amount -> amount == 1)) {
                return i + 4;
            }
        }

        return 0;
    }

    public int handlePart2(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        for (var i = 0; i < line.length() - 14; i++) {
            var window = line.substring(i, i + 14);

            Map<Character, Integer> count = window.chars()
                                                  .mapToObj(value -> (char) value)
                                                  .collect(new CountingCollector<>());

            if (count.values().stream().allMatch(amount -> amount == 1)) {
                return i + 14;
            }
        }

        return 0;
    }
}
