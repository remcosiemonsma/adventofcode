package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
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

    @Override
    public Integer handlePart2(Stream<String> input) {
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
