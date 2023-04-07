package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();
        var generatorA = new Generator(16807, false, 0, Integer.parseInt(lines.get(0).substring(24)));
        var generatorB = new Generator(48271, false, 0, Integer.parseInt(lines.get(1).substring(24)));

        return countMatches(generatorA, generatorB, 40000000);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();
        var generatorA = new Generator(16807, true, 4, Integer.parseInt(lines.get(0).substring(24)));
        var generatorB = new Generator(48271, true, 8, Integer.parseInt(lines.get(1).substring(24)));

        return countMatches(generatorA, generatorB, 5000000);
    }

    private int countMatches(Generator generatorA, Generator generatorB, int rounds) {
        var matches = 0;

        for (var i = 0; i < rounds; i++) {
            var valueA = generatorA.generateValue();
            var valueB = generatorB.generateValue();

            var bitsA = Long.toBinaryString(valueA);
            var bitsB = Long.toBinaryString(valueB);

            var significantBitsA = bitsA.length() > 16 ? bitsA.substring(bitsA.length() - 16) : bitsA;
            var significantBitsB = bitsB.length() > 16 ? bitsB.substring(bitsB.length() - 16) : bitsB;

            if(significantBitsA.equals(significantBitsB)) {
                matches++;
            }
        }
        return matches;
    }

    private static class Generator {
        private final long factor;
        private final boolean picky;
        private final int criteria;
        private long previousValue;

        private Generator(long factor, boolean picky, int criteria, int previousValue) {
            this.factor = factor;
            this.picky = picky;
            this.criteria = criteria;
            this.previousValue = previousValue;
        }

        long generateValue() {

            if (picky) {
                long value;

                while((value = (previousValue * factor) % 2147483647) % criteria != 0){
                    previousValue = value;
                }

                previousValue = value;

                return value;
            } else {
                long multiply = previousValue * factor;
                long remainder = multiply % 2147483647;
                previousValue = remainder;
                return remainder;
            }
        }
    }
}
