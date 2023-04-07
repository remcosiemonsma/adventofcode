package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day4 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var range = input.findFirst()
                         .orElseThrow(() -> new AssertionError("Eek!"))
                         .split("-");
        
        var start = Integer.parseInt(range[0]);
        var end = Integer.parseInt(range[1]);
        
        var amountMatching = 0;

        for (var password = start; password <= end; password++) {
            var passwordString = Integer.toString(password);

            if (passwordString.length() == 6 && doesPasswordContainSameAdjacentDigits(passwordString) &&
                doesPasswordContainOnlyIncreasingDigits(passwordString)) {
                amountMatching++;
            }
        }

        return amountMatching;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var range = input.findFirst()
                         .orElseThrow(() -> new AssertionError("Eek!"))
                         .split("-");

        var start = Integer.parseInt(range[0]);
        var end = Integer.parseInt(range[1]);
        
        var amountMatching = 0;

        for (var password = start; password <= end; password++) {
            var passwordString = Integer.toString(password);

            if (passwordString.length() == 6 && doesPasswordContainSameAdjacentDigitsWithNoTriplets(passwordString) &&
                doesPasswordContainOnlyIncreasingDigits(passwordString)) {
                amountMatching++;
            }
        }

        return amountMatching;
    }

    private boolean doesPasswordContainSameAdjacentDigitsWithNoTriplets(String password) {
        var charArray = password.toCharArray();

        var c0 = charArray[0];
        var c1 = charArray[1];
        var c2 = charArray[2];
        var c3 = charArray[3];
        var c4 = charArray[4];
        var c5 = charArray[5];

        if (c0 == c1 && c1 != c2) {
            return true;
        }
        if (c1 == c2 && c2 != c3 && c1 != c0) {
            return true;
        }
        if (c2 == c3 && c3 != c4 && c2 != c1) {
            return true;
        }
        if (c3 == c4 && c4 != c5 && c3 != c2) {
            return true;
        }
        return c4 == c5 && c4 != c3;
    }

    private boolean doesPasswordContainSameAdjacentDigits(String password) {
        var charArray = password.toCharArray();

        for (var i = 0; i < charArray.length - 1; i++) {
            var c1 = charArray[i];
            var c2 = charArray[i + 1];

            if (c1 == c2) {
                return true;
            }
        }

        return false;
    }

    private boolean doesPasswordContainOnlyIncreasingDigits(String password) {
        var charArray = password.toCharArray();
        for (var i = 0; i < charArray.length - 1; i++) {
            var c1 = charArray[i];
            var c2 = charArray[i + 1];

            if (c2 < c1) {
                return false;
            }
        }

        return true;
    }
}
