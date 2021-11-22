package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        return (int) input.map(String::trim)
                          .map(s -> s.split(" +"))
                          .filter(s -> isValidTriangle(Integer.parseInt(s[0]), Integer.parseInt(s[1]),
                                                       Integer.parseInt(s[2])))
                          .count();
    }

    private boolean isValidTriangle(int a, int b, int c) {
        return a + b > c && a + c > b && b + c > a;
    }

    public int handlePart2(Stream<String> input) {
        Integer[][] lines = input.map(String::trim)
                                .map(s -> s.split(" +"))
                                .map(s -> Arrays.stream(s).map(Integer::parseInt).toArray(Integer[]::new))
                                .toArray(Integer[][]::new);

        int validTriangles = 0;

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
