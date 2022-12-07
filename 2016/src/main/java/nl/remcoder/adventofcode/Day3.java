package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Stream;

public class Day3 {
    public long handlePart1(Stream<String> input) {
        return input.map(String::trim)
                    .map(s -> s.split(" +"))
                    .filter(s -> isValidTriangle(Integer.parseInt(s[0]), Integer.parseInt(s[1]),
                                                 Integer.parseInt(s[2])))
                    .count();
    }

    private boolean isValidTriangle(int a, int b, int c) {
        return a + b > c && a + c > b && b + c > a;
    }

    public int handlePart2(Stream<String> input) {
        var lines = input.map(String::trim)
                         .map(s -> s.split(" +"))
                         .map(s -> Arrays.stream(s).map(Integer::parseInt).toArray(Integer[]::new))
                         .toArray(Integer[][]::new);

        var validTriangles = 0;

        for (int i = 0; i < lines.length; i += 3) {
            for (int j = 0; j < 3; j++) {
                if (isValidTriangle(lines[i][j], lines[i + 1][j], lines[i + 2][j])) {
                    validTriangles++;
                }
            }
        }

        return validTriangles;
    }
}
