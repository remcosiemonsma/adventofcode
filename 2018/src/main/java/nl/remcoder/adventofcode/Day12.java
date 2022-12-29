package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day12 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var line = lines.get(0);

        var data = line.split(" ")[2];

        var plants = new boolean[1024];

        var centre = 512;
        var index = centre;

        var chars = data.toCharArray();
        for (var value : chars) {
            plants[index++] = value == '#';
        }

        var transforms = lines.stream()
                              .skip(2)
                              .collect(Collectors.toMap(
                                      string -> Arrays.asList(string.charAt(0) == '#', string.charAt(1) == '#',
                                                              string.charAt(2) == '#', string.charAt(3) == '#',
                                                              string.charAt(4) == '#'),
                                      string2 -> string2.charAt(9) == '#'));

        for (int i = 0; i < 20; i++) {
            boolean[] newplants = Arrays.copyOf(plants, plants.length);

            for (int plant = 2; plant < 1022; plant++) {
                List<Boolean>
                        transform =
                        Arrays.asList(plants[plant - 2], plants[plant - 1], plants[plant], plants[plant + 1],
                                      plants[plant + 2]);

                boolean result = transforms.getOrDefault(transform, false);

                newplants[plant] = result;
            }

            plants = newplants;
        }

        int totalPlants = 0;

        for (int i = 0; i < 1024; i++) {
            if (plants[i]) {
                totalPlants += i - centre;
            }
        }

        return totalPlants;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var lines = input.toList();

        var line = lines.get(0);

        var data = line.split(" ")[2];

        var arraySize = 8192;
        var plants = new boolean[arraySize];

        var centre = arraySize / 2;
        var index = centre;

        var chars = data.toCharArray();
        for (var value : chars) {
            plants[index++] = value == '#';
        }

        var transforms = lines.stream()
                              .skip(2)
                              .collect(Collectors.toMap(
                                      string -> Arrays.asList(string.charAt(0) == '#', string.charAt(1) == '#',
                                                              string.charAt(2) == '#', string.charAt(3) == '#',
                                                              string.charAt(4) == '#'),
                                      string2 -> string2.charAt(9) == '#'));

        List<Integer> plantCount = new ArrayList<>();

        while (plantCount.size() < 1000) {
            boolean[] newplants = Arrays.copyOf(plants, plants.length);

            for (int plant = 2; plant < arraySize - 2; plant++) {
                List<Boolean> transform =
                        Arrays.asList(plants[plant - 2], plants[plant - 1], plants[plant], plants[plant + 1],
                                      plants[plant + 2]);

                boolean result = transforms.getOrDefault(transform, false);

                newplants[plant] = result;
            }

            int totalPlants = sumPlantNumbers(plants, centre);

            plantCount.add(totalPlants);

            plants = newplants;
        }

        int growthRateAfter1000Iterations =
                plantCount.get(plantCount.size() - 1) - plantCount.get(plantCount.size() - 2);

        long growthTillEnd = growthRateAfter1000Iterations * (50000000000L - 999);

        return plantCount.get(plantCount.size() - 1) + growthTillEnd;
    }

    private int sumPlantNumbers(boolean[] plants, int centre) {
        int totalPlants = 0;

        for (int i = 0; i < plants.length; i++) {
            if (plants[i]) {
                totalPlants += i - centre;
            }
        }
        return totalPlants;
    }
}
