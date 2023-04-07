package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day20 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var expectedScore = input.mapToInt(Integer::parseInt)
                                 .findFirst()
                                 .orElseThrow(() -> new AssertionError("Eek!"));

        var houses = new House[expectedScore / 10];
        for (var i = 0; i < houses.length; i++) {
            houses[i] = new House(i);
        }

        for (var elf = 1; elf < expectedScore / 10; elf++) {
            for (var i = elf; i < houses.length; i += elf) {
                houses[i].presents += elf * 10;
            }
        }

        return Arrays.stream(houses)
                     .filter(house -> house.presents > expectedScore)
                     .mapToInt(house -> house.number)
                     .min()
                     .orElseThrow(() -> new AssertionError("Ook!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var expectedScore = input.mapToInt(Integer::parseInt)
                                 .findFirst()
                                 .orElseThrow(() -> new AssertionError("Eek!"));

        var houses = new House[expectedScore / 10];
        for (var i = 0; i < houses.length; i++) {
            houses[i] = new House(i);
        }

        for (var elf = 1; elf < expectedScore / 10; elf++) {
            var number = elf;
            for (var i = 0; i < 50 && number < expectedScore / 10; i++) {
                houses[number].presents += elf * 11;
                number += elf;
            }
        }

        return Arrays.stream(houses)
                     .filter(house -> house.presents >= expectedScore)
                     .mapToInt(house -> house.number)
                     .min()
                     .orElseThrow(() -> new AssertionError("Ook!"));
    }

    private static class House {
        private final int number;
        private int presents;

        private House(int number) {
            this.number = number;
        }

        @Override
        public String toString() {
            return "House{" +
                   "number=" + number +
                   ", presents=" + presents +
                   '}';
        }
    }
}
