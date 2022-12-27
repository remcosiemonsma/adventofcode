package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        char previousChar = line.charAt(line.length() - 1);

        var result = 0;

        for (var c : line.toCharArray()) {
            if (c == previousChar) {
                result += c - '0';
            }

            previousChar = c;
        }
        
        return result;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var line = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var result = 0;

        var chars = line.toCharArray();

        for (int i = 0, j = line.length() / 2; i < line.length(); i++, j++) {
            if (chars[i] == chars[j % line.length()]) {
                result += chars[i] - '0';
            }
        }
        
        return result;
    }
}
