package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Day6 implements AdventOfCodeSolution<Integer> {
    private int requiredValue;
    @Override
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();
        
        var grid = new String[512][512];

        for (var line : grid) {
            Arrays.fill(line, "  ");
        }

        var points = parsePoints(lines, grid);

        for (var x = 0; x < grid.length; x++) {
            for (var y = 0; y < grid.length; y++) {
                if (grid[x][y].equals("  ")) {
                    var closestPoint = findClosestPoint(x, y, points);
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

        var largestNonInfiniteArea = Integer.MIN_VALUE;

        for (var point : points) {
            if (point.area != Double.POSITIVE_INFINITY && point.area > largestNonInfiniteArea) {
                largestNonInfiniteArea = (int) point.area;
            }
        }
        
        return largestNonInfiniteArea;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var grid = new String[512][512];

        for (var line : grid) {
            Arrays.fill(line, "  ");
        }

        var points = parsePoints(lines, grid);

        for (var x = 0; x < grid.length; x++) {
            for (var y = 0; y < grid.length; y++) {
                if (isTotalDistanceLowerThanRequiredValue(x, y, points)) {
                    grid[x][y] = "##";
                } else {
                    grid[x][y] = "..";
                }
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
        
        return safeArea;
    }

    public void setRequiredValue(int requiredValue) {
        this.requiredValue = requiredValue;
    }

    private static ArrayList<Point> parsePoints(List<String> lines, String[][] grid) {
        var points = new ArrayList<Point>();

        var firstChar = 'A';
        var secondChar = 'A';

        for (var line : lines) {
            var values = line.split(", ");
            var coords = new int[]{Integer.parseInt(values[0]), Integer.parseInt(values[1])};

            var id = firstChar + "" + secondChar++;

            var point = new Point(id, coords[1], coords[0]);

            points.add(point);

            grid[coords[1]][coords[0]] = id;

            if (secondChar > 'Z') {
                secondChar = 'A';
                firstChar++;
            }
        }
        return points;
    }

    private boolean isTotalDistanceLowerThanRequiredValue(int x, int y, List<Point> points) {
        int totalDistance = 0;

        for (var point : points) {
            int distance = Math.abs(point.x - x) + Math.abs(point.y - y);

            totalDistance += distance;

            if (totalDistance >= requiredValue) {
                return false;
            }
        }

        return totalDistance < requiredValue;
    }
    
    private static Optional<Point> findClosestPoint(int x, int y, List<Point> points) {
        int lowestDistance = Integer.MAX_VALUE;
        Point closestPoint = null;

        var duplicateFound = false;

        for (var point : points) {
            var distance = Math.abs(point.x - x) + Math.abs(point.y - y);

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
            return Optional.ofNullable(closestPoint);
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
