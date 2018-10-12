package nl.remcoder.adventofcode.day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()));

        boolean[][] grid = new boolean[1000][1000];

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

        System.out.println(countActivePixels(grid));
    }

    private static void toggle(boolean[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] = !grid[x][y];
            }
        }
    }

    private static void turnon(boolean[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] = true;
            }
        }
    }

    private static void turnoff(boolean[][] grid, int startx, int starty, int endx, int endy) {
        for (int x = startx; x <= endx; x++) {
            for (int y = starty; y <= endy; y++) {
                grid[x][y] = false;
            }
        }
    }

    private static void printgrid(boolean[][] grid) {
        for (boolean[] row : grid) {
            for (boolean pixel : row) {
                if (pixel) {
                    System.out.print('#');
                } else {
                    System.out.print(' ');
                }
            }
            System.out.print("\n");
        }
    }

    private static long countActivePixels(boolean[][] grid) {
        long count = 0;
        for (boolean[] row : grid) {
            for (boolean pixel : row) {
                if (pixel) {
                    count++;
                }
            }
        }
        return count;
    }
}
