package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.hash.KnotHash;
import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var squaresUsed = 0;

        for (var i = 0; i < 128; i++) {
            var knotHash = KnotHash.getHash((line + "-" + i).chars().toArray());

            for (var c : knotHash.toCharArray()) {
                var binaryString = Integer.toBinaryString(Integer.parseInt(String.valueOf(c), 16));
                for (var b : binaryString.toCharArray()) {
                    if (b == '1') {
                        squaresUsed++;
                    }
                }
            }
        }

        return squaresUsed;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var line = input.findFirst()
                        .orElseThrow(() -> new AssertionError("Eek!"));

        var grid = new int[128][128];

        for (var i = 0; i < 128; i++) {
            var knotHash = KnotHash.getHash((line + "-" + i).chars().toArray());
            var knotHashCharArray = knotHash.toCharArray();

            for (var j = 0; j < knotHash.length(); j++) {
                var c = knotHashCharArray[j];

                var binaryStringBuilder = new StringBuilder(
                        Integer.toBinaryString(
                                       Integer.parseInt(String.valueOf(c), 16))
                               .replaceAll("1", "#")
                               .replaceAll("0", " "));

                while (binaryStringBuilder.length() < 4) {
                    binaryStringBuilder.insert(0, " ");
                }

                var chars = binaryStringBuilder.toString().toCharArray();

                for (var k = 0; k < binaryStringBuilder.length(); k++) {
                    grid[i][(j * 4) + k] = chars[k];
                }
            }
        }

        var groupCount = 0;

        for (var i = 0; i < grid.length; i++) {
            var chars = grid[i];
            for (var j = 0; j < chars.length; j++) {
                if (chars[j] == '#') {
                    groupCount++;
                    flagGroup(grid, i, j, groupCount);
                }
            }
        }

        return groupCount;
    }

    private void flagGroup(int[][] grid, int i, int j, int groupCount) {
        if (i >= 0 && i < 128 && j >= 0 && j < 128 && grid[i][j] == '#') {
            grid[i][j] = groupCount;
            flagGroup(grid, i + 1, j, groupCount);
            flagGroup(grid, i - 1, j, groupCount);
            flagGroup(grid, i, j + 1, groupCount);
            flagGroup(grid, i, j - 1, groupCount);
        }
    }
}
