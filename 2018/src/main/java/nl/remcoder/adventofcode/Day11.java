package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<int[]> {
    @Override
    public int[] handlePart1(Stream<String> input) {
        var grid = generateGrid(input);

        var previoushighestpowerlevel = Integer.MIN_VALUE;

        var highestX = 0;
        var highestY = 0;

        for (var y = 0; y < 297; y++) {
            for (var x = 0; x < 297; x++) {
                var powerlevel = 0;
                for (var i = 0; i < 3; i++) {
                    for (var j = 0; j < 3; j++) {
                        powerlevel += grid[y + i][x + j];
                    }
                }

                if (powerlevel > previoushighestpowerlevel) {
                    previoushighestpowerlevel = powerlevel;
                    highestX = x + 1;
                    highestY = y + 1;
                }
            }
        }

        return new int[] {highestX, highestY};
    }

    @Override
    public int[] handlePart2(Stream<String> input) {
        var grid = generateGrid(input);

        var previoushighestpowerlevel = Integer.MIN_VALUE;

        var highestX = 0;
        var highestY = 0;
        var size = 0;

        for (var s = 0; s < 300; s++) {
            for (var y = 0; y < 300 - s; y++) {
                for (var x = 0; x < 300 - s; x++) {
                    var powerlevel = 0;

                    for (var i = 0; i < s; i++) {
                        for (var j = 0; j < s; j++) {
                            powerlevel += grid[y + i][x + j];
                        }
                    }

                    if (powerlevel > previoushighestpowerlevel) {
                        previoushighestpowerlevel = powerlevel;
                        highestX = x + 1;
                        highestY = y + 1;
                        size = s;
                    }
                }
            }
        }

        return new int[] {highestX, highestY, size};
    }

    private int[][] generateGrid(Stream<String> input) {
        var gridSerialNumber = input.findFirst()
                                    .map(Integer::parseInt)
                                    .orElseThrow(() -> new AssertionError("Eek!"));

        var grid = new int[300][300];

        for (var y = 0; y < 300; y++) {
            for (var x = 0; x < 300; x++) {
                var xcoord = x + 1;
                var ycoord = y + 1;

                var rackId = xcoord + 10;
                var beginningpowerlevel = rackId * ycoord;
                var increasedpowerlevel = beginningpowerlevel + gridSerialNumber;
                var multipliedpowerelevel = increasedpowerlevel * rackId;

                var hundredsdigit = (int) Math.floor((multipliedpowerelevel % 1000) / 100d);

                var subtractedpowerlevel = hundredsdigit - 5;

                grid[y][x] = subtractedpowerlevel;
            }
        }
        return grid;
    }
}
