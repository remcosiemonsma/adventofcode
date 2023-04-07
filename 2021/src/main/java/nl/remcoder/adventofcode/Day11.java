package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var octopi = input.map(String::chars)
                          .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                             .mapToObj(Octopus::new)
                                             .toArray(Octopus[]::new))
                          .toArray(Octopus[][]::new);

        var amountOfFlashes = 0;

        for (var step = 0; step < 100; step++) {
            amountOfFlashes += incrementEnergy(octopi);
        }

        return amountOfFlashes;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var octopi = input.map(String::chars)
                          .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                             .mapToObj(Octopus::new)
                                             .toArray(Octopus[]::new))
                          .toArray(Octopus[][]::new);

        var amountOfOctopuses = octopi.length * octopi[0].length;

        var amountOfFlashes = 0;

        var step = 0;

        while (amountOfFlashes != amountOfOctopuses) {
            amountOfFlashes = incrementEnergy(octopi);
            step++;
        }

        return step;
    }

    private int incrementEnergy(Octopus[][] octopusMap) {
        for (var x = 0; x < octopusMap.length; x++) {
            for (var y = 0; y < octopusMap[x].length; y++) {
                incrementEnergy(octopusMap, x, y);
            }
        }

        var amountOfFlashes = 0;

        for (var line : octopusMap) {
            for (var octopus : line) {
                if (octopus.hasFlashed) {
                    amountOfFlashes++;
                    octopus.energyLevel = 0;
                }
                octopus.hasFlashed = false;
            }
        }

        return amountOfFlashes;
    }

    private void incrementEnergy(Octopus[][] octopi, int x, int y) {
        if (x >= 0 && y >= 0 && x < octopi.length && y < octopi[x].length) {
            var octopus = octopi[x][y];
            if (!octopus.hasFlashed) {
                octopus.energyLevel++;
                if (octopus.energyLevel > 9) {
                    flashOctopus(octopi, x, y);
                }
            }
        }
    }

    private void flashOctopus(Octopus[][] octopi, int x, int y) {
        octopi[x][y].hasFlashed = true;
        incrementEnergy(octopi, x + 1, y + 1);
        incrementEnergy(octopi, x + 1, y);
        incrementEnergy(octopi, x + 1, y - 1);
        incrementEnergy(octopi, x, y + 1);
        incrementEnergy(octopi, x, y - 1);
        incrementEnergy(octopi, x - 1, y + 1);
        incrementEnergy(octopi, x - 1, y);
        incrementEnergy(octopi, x - 1, y - 1);
    }

    private static class Octopus {
        int energyLevel;
        boolean hasFlashed = false;

        public Octopus(int energyLevel) {
            this.energyLevel = energyLevel;
        }
    }
}
