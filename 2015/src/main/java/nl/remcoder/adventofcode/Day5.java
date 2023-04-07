package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return input.filter(line -> !(line.contains("ab") || line.contains("cd") || line.contains("pq") ||
                                      line.contains("xy")))
                    .filter(line -> line.chars()
                                        .filter(value -> value == 'a' || value == 'e' || value == 'o' || value == 'i' ||
                                                         value == 'u')
                                        .count() >= 3)
                    .map(String::toCharArray)
                    .filter(this::isNiceString)
                    .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return input.filter(this::doesStringFulfillFirstRule)
                    .filter(this::doesStringFulfillSecondRule)
                    .count();
    }

    private boolean isNiceString(char[] chars) {
        var previouschar = chars[0];
        var niceString = false;

        for (var i = 1; i < chars.length; i++) {
            if (chars[i] == previouschar) {
                niceString = true;
                break;
            }
            previouschar = chars[i];
        }

        return niceString;
    }

    private boolean doesStringFulfillFirstRule(String string) {
        var chars = string.toCharArray();

        for (var i = 0; i < chars.length - 2; i++) {
            var currentchars = new String(new char[]{chars[i], chars[i + 1]});

            for (var j = i + 2; j < chars.length - 1; j++) {
                var otherchars = new String(new char[]{chars[j], chars[j + 1]});

                if (currentchars.equals(otherchars)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean doesStringFulfillSecondRule(String string) {
        var chars = string.toCharArray();

        for (var i = 0; i < chars.length - 2; i++) {
            if (chars[i] == chars[i + 2]) {
                return true;
            }
        }
        return false;
    }
}
