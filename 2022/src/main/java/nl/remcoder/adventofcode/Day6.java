package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day6 {
    public int handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        for (var i = 0; i < line.length() - 4; i++) {
            var window = line.substring(i, i + 4);

            if (window.chars().distinct().count() == 4) {
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

            if (window.chars().distinct().count() == 14) {
                return i + 14;
            }
        }

        return 0;
    }
}
