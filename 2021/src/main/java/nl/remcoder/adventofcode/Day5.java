package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day5 implements AdventOfCodeSolution<Integer> {
    private static final Pattern LINE_PATTERN = Pattern.compile("(\\d+),(\\d+) -> (\\d+),(\\d+)");

    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = new Grid();

        input.map(LINE_PATTERN::matcher)
             .filter(Matcher::matches)
             .map(this::mapToLine)
             .filter(Line::isNonDiagonalLine)
             .forEach(grid::drawLine);

        return grid.overlap;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = new Grid();

        input.map(LINE_PATTERN::matcher)
             .filter(Matcher::matches)
             .map(this::mapToLine)
             .forEach(grid::drawLine);

        return grid.overlap;
    }

    private Line mapToLine(Matcher m) {
        return new Line(new Point(Integer.parseInt(m.group(1)),
                                  Integer.parseInt(m.group(2))),
                        new Point(Integer.parseInt(m.group(3)),
                                  Integer.parseInt(m.group(4))));
    }

    private static class Grid {
        private final int[][] grid = new int[1000][1000];
        private int overlap;

        public void drawLine(Line line) {
            if (line.isHorizontalLine()) {
                int startx;
                int endx;

                if (line.p1.x < line.p2.x) {
                    startx = line.p1.x;
                    endx = line.p2.x;
                } else {
                    startx = line.p2.x;
                    endx = line.p1.x;
                }

                int y = line.p1.y;

                for (int x = startx; x <= endx; x++) {
                    grid[y][x]++;
                    if (grid[y][x] == 2) {
                        overlap++;
                    }
                }
            } else if (line.isVerticalLine()) {
                int starty;
                int endy;

                if (line.p1.y < line.p2.y) {
                    starty = line.p1.y;
                    endy = line.p2.y;
                } else {
                    starty = line.p2.y;
                    endy = line.p1.y;
                }

                int x = line.p1.x;

                for (int y = starty; y <= endy; y++) {
                    grid[y][x]++;
                    if (grid[y][x] == 2) {
                        overlap++;
                    }
                }
            } else {
                Point leftMostPoint;
                Point upperMostPont;
                Point rightMostPoint;

                if (line.p1.x < line.p2.x) {
                    leftMostPoint = line.p1;
                    rightMostPoint = line.p2;
                } else {
                    leftMostPoint = line.p2;
                    rightMostPoint = line.p1;
                }

                if (line.p1.y < line.p2.y) {
                    upperMostPont = line.p1;
                } else {
                    upperMostPont = line.p2;
                }

                var startx = leftMostPoint.x;
                var starty = leftMostPoint.y;
                var length = Math.abs(leftMostPoint.x - rightMostPoint.x);

                if (leftMostPoint == upperMostPont) {
                    for (var i = 0; i <= length; i++) {
                        grid[starty + i][startx + i]++;
                        if (grid[starty + i][startx + i] == 2) {
                            overlap++;
                        }
                    }
                } else {
                    for (var i = 0; i <= length; i++) {
                        grid[starty - i][startx + i]++;
                        if (grid[starty - i][startx + i] == 2) {
                            overlap++;
                        }
                    }
                }
            }
        }
    }

    private record Line(Point p1, Point p2) {
        boolean isNonDiagonalLine() {
            return isHorizontalLine() || isVerticalLine();
        }

        boolean isHorizontalLine() {
            return p1.y == p2.y;
        }

        boolean isVerticalLine() {
            return p1.x == p2.x;
        }
    }

    private record Point(int x, int y) {
    }
}
