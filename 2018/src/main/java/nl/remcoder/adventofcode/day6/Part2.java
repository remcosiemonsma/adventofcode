package nl.remcoder.adventofcode.day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day6/input").toURI()));

        String[][] grid = new String[512][512];

        for (String[] line : grid) {
            Arrays.fill(line, "  ");
        }

        List<Point> points = new ArrayList<>();

        char firstChar = 'A';
        char secondChar = 'A';

        for (String line : lines) {
            String[] values = line.split(", ");
            int[] coords = new int[]{Integer.parseInt(values[0]), Integer.parseInt(values[1])};

            String id = firstChar + "" + secondChar++;

            Point point = new Point(id, coords[1], coords[0]);

            points.add(point);

            grid[coords[1]][coords[0]] = id;

            if (secondChar > 'Z') {
                secondChar = 'A';
                firstChar++;
            }
        }

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid.length; y++) {
//                if (grid[x][y].equals("  ")) {
                    if (isTotalDistanceLowerThanRequiredValue(x, y, points, 10000)) {
                        grid[x][y] = "##";
                    } else {
                        grid[x][y] = "..";
                    }
//                }
            }
        }

        int safeArea = 0;

        for (String[] line : grid) {
            for (String value : line) {
                if ("##".equals(value)) {
                    safeArea++;
                }
            }
        }

        System.out.println(safeArea);
    }

    private static boolean isTotalDistanceLowerThanRequiredValue(int x, int y, List<Point> points, int requiredValue) {
        int totalDistance = 0;

        for (Point point : points) {
            int distance = Math.abs(point.x - x) + Math.abs(point.y - y);

            totalDistance += distance;

            if (totalDistance >= requiredValue) {
                return false;
            }
        }

        return totalDistance < requiredValue;
    }

    private static class Point {
        final String id;
        final int x;
        final int y;
        double area = 1;

        private Point(String id, int x, int y) {
            this.id = id;
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "Point{" +
                   "id='" + id + '\'' +
                   ", x=" + x +
                   ", y=" + y +
                   ", area=" + area +
                   '}';
        }
    }
}
