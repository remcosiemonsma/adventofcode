package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));

        var floorcounter = 0;

        var chars = line.toCharArray();
        for (var c : chars) {
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }
        }

        return floorcounter;
    }

    public int handlePart2(Stream<String> input) {
        var line = input.findFirst()
                           .orElseThrow(() -> new AssertionError("Eek!"));

        var floorcounter = 0;
        var position = 0;

        var chars = line.toCharArray();

        do {
            var c = chars[position++];
            if (c == '(') {
                floorcounter++;
            } else if (c == ')') {
                floorcounter--;
            }
        } while (floorcounter >= 0);

        return position;
    }
}
