package nl.remcoder.adventofcode.day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()));

        long[][] grid = new long[1000][1000];

        for (String instruction : lines) {
            String[] instructionparts = instruction.split("(\\s(?=\\d)| through )");

            String[] start = instructionparts[1].split(",");
            String[] end = instructionparts[2].split(",");

            int startx = Integer.valueOf(start[0]);
            int starty = Integer.valueOf(start[1]);
            int endx = Integer.valueOf(end[0]);
            int endy = Integer.valueOf(end[1]);

            switch (instructionparts[0]) {
                case "toggle":
                    toggle(grid, startx, starty, endx, endy);
                    break;
                case "turn on":
                    turnon(grid, startx, starty, endx, endy);
                    break;
                case "turn off":
                    turnoff(grid, startx, starty, endx, endy);
                    break;
            }
        }

        System.out.println(countPixelValue(grid));
    }

    private static void toggle(long[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] += 2;
            }
        }
    }

    private static void turnon(long[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] += 1;
            }
        }
    }

    private static void turnoff(long[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] -= 1;
                if (grid[x][y] < 0) {
                    grid[x][y] = 0;
                }
            }
        }
    }

    private static long countPixelValue(long[][] grid) {
        long count = 0;
        for (long[] row : grid) {
            for (long pixel : row) {
                count += pixel;
            }
        }
        return count;
    }
}
