package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day20 {
    public int handlePart1(Stream<String> input) {
        int expectedScore = input.mapToInt(Integer::parseInt)
                                 .findFirst()
                                 .getAsInt();

        House[] houses = new House[expectedScore / 10];
        for (int i = 0; i < houses.length; i++) {
            houses[i] = new House(i);
        }

        for (int elf = 1; elf < expectedScore / 10; elf++) {
            for (int i = elf; i < houses.length; i += elf) {
                houses[i].presents += elf * 10;
            }
        }

        return Arrays.stream(houses)
                     .filter(house -> house.presents > expectedScore)
                     .mapToInt(house -> house.number)
                     .min()
                     .getAsInt();
    }

    public int handlePart2(Stream<String> input) {
        int expectedScore = input.mapToInt(Integer::parseInt)
                                 .findFirst()
                                 .getAsInt();

        House[] houses = new House[expectedScore / 10];
        for (int i = 0; i < houses.length; i++) {
            houses[i] = new House(i);
        }

        for (int elf = 1; elf < expectedScore / 10; elf++) {
            int number = elf;
            for (int i = 0; i < 50 && number < expectedScore / 10; i++) {
                houses[number].presents += elf * 11;
                number += elf;
            }
        }

        return Arrays.stream(houses)
                     .filter(house -> house.presents >= expectedScore)
                     .mapToInt(house -> house.number)
                     .min()
                     .getAsInt();
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
