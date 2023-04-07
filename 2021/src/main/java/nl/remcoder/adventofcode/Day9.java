package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Stream;

public class Day9 implements BiAdventOfCodeSolution<Integer, Long> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var heightmap = input.map(String::chars)
                             .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                                .toArray())
                             .toArray(int[][]::new);

        var riskSum = 0;

        for (var y = 0; y < heightmap.length; y++) {
            for (var x = 0; x < heightmap[y].length; x++) {
                var value = heightmap[y][x];

                if ((x == 0 || value < heightmap[y][x - 1]) &&
                    (x == heightmap[y].length - 1 || value < heightmap[y][x + 1]) &&
                    (y == 0 || value < heightmap[y - 1][x]) &&
                    (y == heightmap.length - 1 || value < heightmap[y + 1][x])) {
                    riskSum += value + 1;
                }
            }
        }

        return riskSum;
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var heightmap = input.map(String::chars)
                             .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                                .toArray())
                             .toArray(int[][]::new);

        var sizes = new ArrayList<Long>();

        var value = 10;

        for (var y = 0; y < heightmap.length; y++) {
            for (var x = 0; x < heightmap[y].length; x++) {
                if (heightmap[y][x] < 9) {
                    fillBasin(heightmap, x, y, value);

                    int copy = value;

                    sizes.add(Arrays.stream(heightmap)
                                    .flatMapToInt(Arrays::stream)
                                    .filter(i -> i == copy)
                                    .count());

                    value++;
                }
            }
        }

        return sizes.stream()
                    .sorted(Comparator.reverseOrder())
                    .limit(3)
                    .reduce((l1, l2) -> l1 * l2)
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private void fillBasin(int[][] heightmap, int x, int y, int value) {
        if (heightmap[y][x] < 9) {
            heightmap[y][x] = value;
            if (x < heightmap[y].length - 1) {
                fillBasin(heightmap, x + 1, y, value);
            }
            if (x > 0) {
                fillBasin(heightmap, x - 1, y, value);
            }
            if (y < heightmap.length - 1) {
                fillBasin(heightmap, x, y + 1, value);
            }
            if (y > 0) {
                fillBasin(heightmap, x, y - 1, value);
            }
        }
    }
}
