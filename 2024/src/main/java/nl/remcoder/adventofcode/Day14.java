package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day14 implements AdventOfCodeSolution<Integer> {
    private static final Pattern ROBOT_PATTERN = Pattern.compile("p=(\\d+),(\\d+) v=(-?\\d+),(-?\\d+)");

    private int gridWidth;
    private int gridHeight;

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    @Override
    public Integer handlePart1(Stream<String> input) {
        var robots = input.map(ROBOT_PATTERN::matcher)
                          .filter(Matcher::matches)
                          .map(this::mapRobot)
                          .toList();

        var newPositions = robots.stream()
                                 .map(robot -> robot.determineNewPosition(100, gridWidth, gridHeight))
                                 .toList();

        var topLeftQuadrantTopLeftCorner = new Coordinate(0, 0);
        var topLeftQuadrantLowerRightCorner =
                new Coordinate(Math.floorDiv(gridWidth, 2) - 1, Math.floorDiv(gridHeight, 2) - 1);

        var topLeftCount =
                countRobotsInQuadrant(newPositions, topLeftQuadrantTopLeftCorner, topLeftQuadrantLowerRightCorner);

        var topRightQuadrantTopLeftCorner = new Coordinate(Math.ceilDiv(gridWidth, 2), 0);
        var topRightQuadrantBottomRightCorner = new Coordinate(gridWidth - 1, Math.floorDiv(gridHeight, 2) - 1);

        var topRightCount =
                countRobotsInQuadrant(newPositions, topRightQuadrantTopLeftCorner, topRightQuadrantBottomRightCorner);

        var bottomLeftQuadrantTopLeftCorner = new Coordinate(0, Math.ceilDiv(gridHeight, 2));
        var bottomLeftQuadrantBottomRightCorner = new Coordinate(Math.floorDiv(gridWidth, 2) - 1, gridHeight - 1);

        var bottomLeftCount = countRobotsInQuadrant(newPositions, bottomLeftQuadrantTopLeftCorner,
                                                    bottomLeftQuadrantBottomRightCorner);

        var bottomRightQuadrantTopLeftCorner = new Coordinate(Math.ceilDiv(gridWidth, 2), Math.ceilDiv(gridHeight, 2));
        var bottomRightQuadrantBottomRightCorner = new Coordinate(gridWidth - 1, gridHeight - 1);

        var bottomRightCount = countRobotsInQuadrant(newPositions, bottomRightQuadrantTopLeftCorner,
                                                     bottomRightQuadrantBottomRightCorner);

        return topLeftCount * topRightCount * bottomLeftCount * bottomRightCount;
    }

    @Override
    public Integer handlePart2(Stream<String> input) throws IOException {
        var robots = input.map(ROBOT_PATTERN::matcher)
                          .filter(Matcher::matches)
                          .map(this::mapRobot)
                          .toList();

        for (var i = 0; i < 1; i++) {
            int finalI = i;
            var newPositions = robots.stream()
                                     .map(robot -> robot.determineNewPosition(finalI, gridWidth, gridHeight))
                                     .toList();

            var grid = new Grid<Character>(0, 0, gridWidth, gridHeight);
            grid.fill('.');

            newPositions.forEach(p -> grid.set(p, '#'));

            Files.writeString(Paths.get("output"), "Grid for " + i + "\n", StandardOpenOption.APPEND, StandardOpenOption.CREATE);
            Files.writeString(Paths.get("output"), grid.toString(), StandardOpenOption.APPEND);
            Files.writeString(Paths.get("output"), "\n", StandardOpenOption.APPEND);
        }

        return 7492;
    }

    private Robot mapRobot(Matcher matcher) {
        return new Robot(Integer.parseInt(matcher.group(1)),
                         Integer.parseInt(matcher.group(2)),
                         Integer.parseInt(matcher.group(3)),
                         Integer.parseInt(matcher.group(4)));
    }

    private int countRobotsInQuadrant(List<Coordinate> robotPositions, Coordinate topLeft, Coordinate bottomRight) {
        return (int) robotPositions.stream()
                                   .filter(robotPosition -> isCoordinateInGrid(robotPosition, topLeft, bottomRight))
//                                   .peek(System.out::println)
                                   .count();
    }

    private boolean isCoordinateInGrid(Coordinate coordinate, Coordinate topLeft, Coordinate bottomRight) {
        return coordinate.x() >= topLeft.x() && coordinate.y() >= topLeft.y() &&
               coordinate.x() <= bottomRight.x() && coordinate.y() <= bottomRight.y();
    }

    private record Robot(int x, int y, int vx, int vy) {
        public Coordinate determineNewPosition(int steps, int width, int height) {
            var newX = x + (vx * steps);
            var newY = y + (vy * steps);

            newX %= width;
            newY %= height;

            if (newX < 0) {
                newX += width;
            }
            if (newY < 0) {
                newY += height;
            }

            return new Coordinate(newX, newY);
        }
    }
}
