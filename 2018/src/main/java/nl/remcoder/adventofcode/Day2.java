package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day2 implements BiAdventOfCodeSolution<Integer, String> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var strings = input.toList();
        
        var amountTwice = 0;
        var amountThrice = 0;

        for (var s : strings) {
            if (doesStringContainCharTwice(s)) {
                amountTwice++;
            }
            if (doesStringContainCharThreeTimes(s)) {
                amountThrice++;
            }
        }
        return amountTwice * amountThrice;
    }

    @Override
    public String handlePart2(Stream<String> input) {
        var strings = input.toList();

        var first = "";
        var second = "";

        one: for (var i = 0; i < strings.size(); i++) {
            var one = strings.get(i);

            two: for (var j = i + 1; j < strings.size(); j++) {
                var two = strings.get(j);

                if (one.length() != two.length()) {
                    continue;
                }

                int differences = 0;

                for (var k = 0; k < one.length(); k++) {
                    if (one.charAt(k) != two.charAt(k)) {
                        differences++;
                    }

                    if (differences > 1) {
                        continue two;
                    }
                }

                if (differences == 1) {
                    first = one;
                    second = two;
                    break one;
                }
            }
        }

        var sb = new StringBuilder();

        for (int k = 0; k < first.length(); k++) {
            if (first.charAt(k) == second.charAt(k)) {
                sb.append(first.charAt(k));
            }
        }
        
        return sb.toString();
    }

    private boolean doesStringContainCharTwice(String s) {
        var charAmounts = new int[26];

        s.chars()
         .map(c -> c - 'a')
         .forEach(c1 -> charAmounts[c1]++);

        return Arrays.stream(charAmounts)
                     .anyMatch(amount -> amount == 2);
    }

    private boolean doesStringContainCharThreeTimes(String s) {
        var charAmounts = new int[26];

        s.chars()
         .map(c -> c - 'a')
         .forEach(c1 -> charAmounts[c1]++);

        return Arrays.stream(charAmounts)
                     .anyMatch(amount -> amount == 3);
    }
}
