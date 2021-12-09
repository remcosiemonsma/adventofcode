package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class Day9 {
    public int handlePart1(Stream<String> input) {
        int[][] heightmap = input.map(String::chars)
                                 .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                                    .toArray())
                                 .toArray(int[][]::new);

        int riskSum = 0;

        for (int y = 0; y < heightmap.length; y++) {
            for (int x = 0; x < heightmap[y].length; x++) {
                int value = heightmap[y][x];

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

    public long handlePart2(Stream<String> input) {
        int[][] heightmap = input.map(String::chars)
                                 .map(chars -> chars.map(c -> Character.digit((char) c, 10))
                                                    .toArray())
                                 .toArray(int[][]::new);

        List<Long> sizes = new ArrayList<>();

        int value = 10;

        for (int y = 0; y < heightmap.length; y++) {
            for (int x = 0; x < heightmap[y].length; x++) {
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
                    .get();
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
