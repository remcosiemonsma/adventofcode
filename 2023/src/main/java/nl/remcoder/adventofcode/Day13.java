package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.stream.CombiningCollector;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day13 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        return input.collect(new CombiningCollector<>(String::toCharArray,
                                                      String::isBlank,
                                                      CombiningCollector.ListBag::new))
                    .map(bag -> ((CombiningCollector.ListBag<char[]>) bag).toList()
                                                                          .toArray(char[][]::new))
                    .mapToInt(image -> calculateReflectionScore(image, 0))
                    .sum();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        return input.collect(new CombiningCollector<>(String::toCharArray,
                                                      String::isBlank,
                                                      CombiningCollector.ListBag::new))
                    .map(bag -> ((CombiningCollector.ListBag<char[]>) bag).toList()
                                                                          .toArray(char[][]::new))
                    .mapToInt(image -> calculateReflectionScore(image, 1))
                    .sum();
    }

    private int calculateReflectionScore(char[][] image, int wantedDifference) {
        return ((findHorizontalMirrorLine(image, wantedDifference) + 1) * 100) +
               (findVerticalMirrorLines(image, wantedDifference) + 1);
    }

    private int findHorizontalMirrorLine(char[][] image, int wantedDifference) {
        for (var y = 0; y < image.length - 1; y++) {
            if (rowMatch(image, y, wantedDifference)) {
                var differences = 0;
                for (int y1 = y, y2 = y + 1; y1 >= 0 && y2 < image.length; y1--, y2++) {
                    differences += countRowDifferences(image, y1, y2);
                    if (differences > wantedDifference) {
                        break;
                    }
                }
                if (differences == wantedDifference) {
                    return y;
                }
            }
        }
        return -1;
    }

    private int findVerticalMirrorLines(char[][] image, int wantedDifference) {
        for (var x = 0; x < image[0].length - 1; x++) {
            if (columnMatch(image, x, wantedDifference)) {
                var differences = 0;
                for (int x1 = x, x2 = x + 1; x1 >= 0 && x2 < image[0].length; x1--, x2++) {
                    differences += countColumnDifferences(image, x1, x2);
                    if (differences > wantedDifference) {
                        break;
                    }
                }
                if (differences == wantedDifference) {
                    return x;
                }
            }
        }

        return -1;
    }

    private boolean rowMatch(char[][] image, int y, int wantedDifference) {
        if (wantedDifference == 0) {
            return Arrays.equals(image[y], image[y + 1]);
        } else {
            var firstLine = image[y];
            var secondLine = image[y + 1];

            var differences = 0;

            for (var x = 0; x < firstLine.length; x++) {
                if (firstLine[x] != secondLine[x]) {
                    differences++;
                }
            }

            return differences == 0 || differences == wantedDifference;
        }
    }

    private int countRowDifferences(char[][] image, int y, int y2) {
        var firstLine = image[y];
        var secondLine = image[y2];

        var differences = 0;

        for (var x = 0; x < firstLine.length; x++) {
            if (firstLine[x] != secondLine[x]) {
                differences++;
            }
        }
        return differences;
    }

    private boolean columnMatch(char[][] image, int x, int wantedDifference) {
        var differences = 0;

        for (var line : image) {
            if (line[x] != line[x + 1]) {
                differences++;
            }
        }

        return differences == 0 || differences == wantedDifference;
    }

    private int countColumnDifferences(char[][] image, int x1, int x2) {
        var differences = 0;

        for (var line : image) {
            if (line[x1] != line[x2]) {
                differences++;
            }
        }

        return differences;
    }

    private void printImage(char[][] image, int bestHorizontal, int bestVertical, int score) {
        for (var y = 0; y < image.length; y++) {
            var line = image[y];
            for (var x = 0; x < line.length; x++) {
                if (y == bestHorizontal) {
                    System.out.print("\u001B[32m" + line[x]);
                } else if (x == bestVertical) {
                    System.out.print("\u001B[31m" + line[x]);
                } else {
                    System.out.print("\u001B[37m" + line[x]);
                }
            }
            System.out.println();
        }

        System.out.println("\u001B[33mScore: " + score);
        System.out.println();
    }
}
