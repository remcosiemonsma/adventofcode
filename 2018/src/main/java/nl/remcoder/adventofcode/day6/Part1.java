package nl.remcoder.adventofcode.day6;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Part1 {
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
                if (grid[x][y].equals("  ")) {
                    Optional<Point> closestPoint = findClosestPoint(x, y, points);
                    if (closestPoint.isPresent()) {
                        grid[x][y] = closestPoint.get().id.toLowerCase();
                        if (x == 0 || y == 0 || x == grid.length - 1 || y == grid.length -1) {
                            closestPoint.get().area = Double.POSITIVE_INFINITY;
                        } else if (closestPoint.get().area != Double.POSITIVE_INFINITY) {
                            closestPoint.get().area++;
                        }
                    } else {
                        grid[x][y] = "..";
                    }
                }
            }
        }

        int largestNonInfiniteArea = Integer.MIN_VALUE;

        for (Point point : points) {
            if (point.area != Double.POSITIVE_INFINITY && point.area > largestNonInfiniteArea) {
                largestNonInfiniteArea = (int) point.area;
            }
        }

        System.out.println(largestNonInfiniteArea);
    }

    private static Optional<Point> findClosestPoint(int x, int y, List<Point> points) {
        int lowestDistance = Integer.MAX_VALUE;
        Point closestPoint = null;

        boolean duplicateFound = false;

        for (Point point : points) {
            int distance = Math.abs(point.x - x) + Math.abs(point.y - y);

            if (distance < lowestDistance) {
                lowestDistance = distance;
                closestPoint = point;
                duplicateFound = false;
            } else if (distance == lowestDistance) {
                duplicateFound = true;
            }
        }

        if (duplicateFound) {
            return Optional.empty();
        } else {
            return Optional.of(closestPoint);
        }
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
