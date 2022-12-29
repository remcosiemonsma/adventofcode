package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = new int[1024][1024];

        input.map(s -> s.split("[^\\d]"))
             .map(this::parseStringToIntPart1)
             .forEach(strings -> parseDataToGridPart1(strings, grid));

        var square = 0;

        for (var line : grid) {
            for (var pixel : line) {
                if (pixel >= 2) {
                    square++;
                }
            }
        }
        
        return square;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = new int[1024][1024];

        var nonOverlappingClaims = IntStream.rangeClosed(1, 1349).boxed().collect(Collectors.toList());
        
        input.map(s -> s.split("[^\\d]"))
             .map(this::parseStringToIntPart2)
             .forEach(strings -> parseDataToGridPart2(strings, grid, nonOverlappingClaims));

        return nonOverlappingClaims.get(0);
    }

    private int[] parseStringToIntPart1(String[] strings) {
        var data = new int[4];

        data[0] = Integer.parseInt(strings[4]);
        data[1] = Integer.parseInt(strings[5]);
        data[2] = Integer.parseInt(strings[7]);
        data[3] = Integer.parseInt(strings[8]);

        return data;
    }

    private void parseDataToGridPart1(int[] data, int[][] grid) {
        for (var x = data[0]; x < data[0] + data[2]; x++) {
            for (var y = data[1]; y < data[1] + data[3]; y++) {
                grid[x][y]++;
            }
        }
    }

    private int[] parseStringToIntPart2(String[] strings) {
        var data = new int[5];

        data[0] = Integer.parseInt(strings[1]);
        data[1] = Integer.parseInt(strings[4]);
        data[2] = Integer.parseInt(strings[5]);
        data[3] = Integer.parseInt(strings[7]);
        data[4] = Integer.parseInt(strings[8]);

        return data;
    }

    private void parseDataToGridPart2(int[] data, int[][] grid, List<Integer> nonOverlappingClaims) {
        for (var x = data[1]; x < data[1] + data[3]; x++) {
            for (var y = data[2]; y < data[2] + data[4]; y++) {
                if (grid[x][y] == 0) {
                    grid[x][y] = data[0];
                } else {
                    nonOverlappingClaims.remove((Integer) data[0]);
                    nonOverlappingClaims.remove((Integer) grid[x][y]);
                    grid[x][y] = -1;
                }
            }
        }
    }
}
