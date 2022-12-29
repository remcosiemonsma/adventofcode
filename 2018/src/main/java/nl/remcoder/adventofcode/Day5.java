package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var polymer = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        var newPolymer = reducePolymer(polymer);

        while (!polymer.equals(newPolymer)) {
            polymer = newPolymer;
            newPolymer = reducePolymer(polymer);
        }
        
        return newPolymer.length();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var polymer = input.findFirst().orElseThrow(() -> new AssertionError("Eek!"));

        int shortestPolymerLength = Integer.MAX_VALUE;

        for (var c = 'a'; c <= 'z'; c++) {
            var removedPolymer = polymer.replaceAll("[" + c + Character.toUpperCase(c) + "]", "");

            var previousPolymer = removedPolymer;
            var newPolymer = reducePolymer(removedPolymer);

            while (!previousPolymer.equals(newPolymer)) {
                previousPolymer = newPolymer;
                newPolymer = reducePolymer(previousPolymer);
            }

            if (newPolymer.length() < shortestPolymerLength) {
                shortestPolymerLength = newPolymer.length();
            }
        }
        
        return shortestPolymerLength;
    }

    private String reducePolymer(String polymer) {
        for (var i = 0; i < polymer.length() - 1; i++) {
            var c1 = polymer.charAt(i);
            var c2 = polymer.charAt(i + 1);

            if (Character.isLowerCase(c1)) {
                if (Character.isUpperCase(c2)) {
                    var c1Upper = Character.toUpperCase(c1);
                    if (c1Upper == c2) {
                        var sb = new StringBuilder(polymer);

                        sb.delete(i, i + 2);

                        return sb.toString();
                    }
                }
            } else {
                if (Character.isLowerCase(c2)) {
                    var c1Lower = Character.toLowerCase(c1);
                    if (c1Lower == c2) {
                        var sb = new StringBuilder(polymer);

                        sb.delete(i, i + 2);

                        return sb.toString();
                    }
                }
            }
        }

        return polymer;
    }
}
