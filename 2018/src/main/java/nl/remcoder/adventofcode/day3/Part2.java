package nl.remcoder.adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {
    public static void main(String[] args) throws Exception {
        int[][] grid = new int[1024][1024];

        List<Integer> nonOverlappingClaims = IntStream.rangeClosed(1, 1349).boxed().collect(Collectors.toList());

        Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))
             .map(s -> s.split("[^\\d]"))
             .map(Part2::parseStringToInt)
             .forEach(strings -> parseDataToGrid(strings, grid, nonOverlappingClaims));

        for (int[] line : grid) {
            System.out.println(Arrays.toString(line));
        }

        System.out.println(nonOverlappingClaims);
    }

    private static int[] parseStringToInt(String[] strings) {
        int[] data = new int[5];

        data[0] = Integer.parseInt(strings[1]);
        data[1] = Integer.parseInt(strings[4]);
        data[2] = Integer.parseInt(strings[5]);
        data[3] = Integer.parseInt(strings[7]);
        data[4] = Integer.parseInt(strings[8]);

        return data;
    }

    private static void parseDataToGrid(int[] data, int[][] grid, List<Integer> nonOverlappingClaims) {
        for (int x = data[1]; x < data[1] + data[3]; x++) {
            for (int y = data[2]; y < data[2] + data[4]; y++) {
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
