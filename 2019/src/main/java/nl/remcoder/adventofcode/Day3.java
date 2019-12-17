package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        String line1 = lines.get(0);
        String line2 = lines.get(1);

        String[] path1 = line1.split(",");
        String[] path2 = line2.split(",");

        int[][] grid = new int[32384][32384];

        grid[16192][16192] = 4;

        fillGridWithPath(path1, grid, 1);
        List<Point> intersections = fillGridWithPath(path2, grid, 2);

        int shortestDistance = Integer.MAX_VALUE;

        for (Point intersection : intersections) {
            int distance = Math.abs(intersection.x - 16192) + Math.abs(intersection.y - 16192);

            if (distance < shortestDistance) {
                shortestDistance = distance;
            }
        }

        return shortestDistance;
    }

    public int handlePart2(Stream<String> input) {
        List<String> lines = input.collect(Collectors.toList());

        String line1 = lines.get(0);
        String line2 = lines.get(1);

        String[] path1 = line1.split(",");
        String[] path2 = line2.split(",");

        int[][] grid = new int[32384][32384];

        grid[16192][16192] = 4;

        fillGridWithPath(path1, grid, 1);
        List<Point> intersections = fillGridWithPath(path2, grid, 2);

        Map<Point, Integer> totalDistances = new HashMap<>();

        for (Point point : intersections) {
            int steps = calculateStepsToPoint(path1, point);
            steps += calculateStepsToPoint(path2, point);

            totalDistances.put(point, steps);
        }

        return totalDistances.values()
                             .stream()
                             .min(Comparator.naturalOrder())
                             .get();
    }

    private List<Point> fillGridWithPath(String[] path1, int[][] grid, int pathNumber) {
        List<Point> intersections = new ArrayList<>();

        int posx = 16192;
        int posy = 16192;

        for (String segment : path1) {
            int distance = Integer.parseInt(segment.substring(1));
            switch (segment.charAt(0)) {
                case 'D':
                    for (int y = 0; y < distance; y++) {
                        grid[++posx][posy] |= pathNumber;
                        if (grid[posx][posy] == 3) {
                            Point point = new Point();
                            point.x = posx;
                            point.y = posy;

                            intersections.add(point);
                        }
                    }
                    break;
                case 'U':
                    for (int y = 0; y < distance; y++) {
                        grid[--posx][posy] |= pathNumber;
                        if (grid[posx][posy] == 3) {
                            Point point = new Point();
                            point.x = posx;
                            point.y = posy;

                            intersections.add(point);
                        }
                    }
                    break;
                case 'L':
                    for (int x = 0; x < distance; x++) {
                        grid[posx][--posy] |= pathNumber;
                        if (grid[posx][posy] == 3) {
                            Point point = new Point();
                            point.x = posx;
                            point.y = posy;

                            intersections.add(point);
                        }
                    }
                    break;
                case 'R':
                    for (int x = 0; x < distance; x++) {
                        grid[posx][++posy] |= pathNumber;
                        if (grid[posx][posy] == 3) {
                            Point point = new Point();
                            point.x = posx;
                            point.y = posy;

                            intersections.add(point);
                        }
                    }
                    break;
            }
        }

        return intersections;
    }

    private int calculateStepsToPoint(String[] path1, Point point) {
        int posx = 16192;
        int posy = 16192;

        int steps = 0;

        outer: for (String segment : path1) {
            int distance = Integer.parseInt(segment.substring(1));

            boolean posxequals = false;
            boolean posyequals = false;

            if (posx == point.x) {
                posxequals = true;
            }
            if (posy == point.y) {
                posyequals = true;
            }

            if (!posxequals && !posyequals) {
                steps += distance;
            }

            switch (segment.charAt(0)) {
                case 'D':
                    for (int y = 0; y < distance; y++) {
                        if (posyequals) {
                            if (posx == point.x) {
                                break outer;
                            }
                            steps++;
                        }
                        posx++;
                    }
                    break;
                case 'U':
                    for (int y = 0; y < distance; y++) {
                        if (posyequals) {
                            if (posx == point.x) {
                                break outer;
                            }
                            steps++;
                        }
                        posx--;
                    }
                    break;
                case 'L':
                    for (int x = 0; x < distance; x++) {
                        if (posxequals) {
                            if (posy == point.y) {
                                break outer;
                            }
                            steps++;
                        }
                        posy--;
                    }
                    break;
                case 'R':
                    for (int x = 0; x < distance; x++) {
                        if (posxequals) {
                            if (posy == point.y) {
                                break outer;
                            }
                            steps++;
                        }
                        posy++;
                    }
                    break;
            }
        }
        return steps;
    }

    private static class Point {
        int x;
        int y;

        @Override
        public String toString() {
            return "Point{" +
                   "x=" + x +
                   ", y=" + y +
                   '}';
        }
    }
}
