package nl.remcoder.adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Part1 {
    public static void main(String[] args) throws Exception {
        int[][] grid = new int[1024][1024];

        Files.lines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI()))
             .map(s -> s.split("[^\\d]"))
             .map(Part1::parseStringToInt)
             .forEach(strings -> parseDataToGrid(strings, grid));

        int square = 0;

        for (int[] line : grid) {
            for (int pixel : line) {
                if (pixel >= 2) {
                    square++;
                }
            }
        }

        System.out.println(square);
    }

    private static int[] parseStringToInt(String[] strings) {
        int[] data = new int[4];

        data[0] = Integer.parseInt(strings[4]);
        data[1] = Integer.parseInt(strings[5]);
        data[2] = Integer.parseInt(strings[7]);
        data[3] = Integer.parseInt(strings[8]);

        return data;
    }

    private static void parseDataToGrid(int[] data, int[][] grid) {
        for (int x = data[0]; x < data[0] + data[2]; x++) {
            for (int y = data[1]; y < data[1] + data[3]; y++) {
                grid[x][y]++;
            }
        }
    }
}
