package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        var fishes = parseInput(input);

        spawnFishes(fishes, 80);

        return Arrays.stream(fishes).sum();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var fishes = parseInput(input);

        spawnFishes(fishes, 256);

        return Arrays.stream(fishes).sum();
    }

    private long[] parseInput(Stream<String> input) {
        var collect = input.map(s -> s.split(","))
                           .flatMap(Arrays::stream)
                           .collect(Collectors.groupingBy(s -> s, Collectors.toList()));

        var fishes = new long[9];

        fishes[0] = collect.getOrDefault("0", List.of()).size();
        fishes[1] = collect.getOrDefault("1", List.of()).size();
        fishes[2] = collect.getOrDefault("2", List.of()).size();
        fishes[3] = collect.getOrDefault("3", List.of()).size();
        fishes[4] = collect.getOrDefault("4", List.of()).size();
        fishes[5] = collect.getOrDefault("5", List.of()).size();
        fishes[6] = collect.getOrDefault("6", List.of()).size();
        fishes[7] = collect.getOrDefault("7", List.of()).size();
        fishes[8] = collect.getOrDefault("8", List.of()).size();
        return fishes;
    }

    private void spawnFishes(long[] fishes, int days) {
        for (int day = 0; day < days; day++) {
            var temp = Arrays.copyOf(fishes, 9);

            fishes[7] = fishes[8];
            fishes[6] = temp[7];
            fishes[5] = temp[6];
            fishes[4] = temp[5];
            fishes[3] = temp[4];
            fishes[2] = temp[3];
            fishes[1] = temp[2];
            fishes[0] = temp[1];
            fishes[6] += temp[0];
            fishes[8] = temp[0];
        }
    }
}
