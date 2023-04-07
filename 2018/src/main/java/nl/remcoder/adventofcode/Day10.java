package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day10 implements BiAdventOfCodeSolution<String, Integer> {
    @Override
    public String handlePart1(Stream<String> input) {
        var lightPoints = input.map(this::parseStringToLightPoint)
                               .toList();

        var previousHighestX = findHighestX(lightPoints);
        var previousHighestY = findHighestY(lightPoints);
        var previousLowestX = findLowestX(lightPoints);
        var previousLowestY = findLowestY(lightPoints);

        lightPoints.forEach(LightPoint::move);

        var currentHighestX = findHighestX(lightPoints);
        var currentHighestY = findHighestY(lightPoints);
        var currentLowestX = findLowestX(lightPoints);
        var currentLowestY = findLowestY(lightPoints);

        while (previousHighestX > currentHighestX && previousHighestY > currentHighestY &&
               previousLowestX < currentLowestX && previousLowestY < currentLowestY) {
            lightPoints.forEach(LightPoint::move);

            previousHighestX = currentHighestX;
            previousHighestY = currentHighestY;
            previousLowestX = currentLowestX;
            previousLowestY = currentLowestY;

            currentHighestX = findHighestX(lightPoints);
            currentHighestY = findHighestY(lightPoints);
            currentLowestX = findLowestX(lightPoints);
            currentLowestY = findLowestY(lightPoints);
        }

        lightPoints.forEach(LightPoint::moveBack);

        var xdiff = previousHighestX - previousLowestX;
        var ydiff = previousHighestY - previousLowestY;

        var grid = new char[ydiff + 1][xdiff + 1];

        for (var line : grid) {
            Arrays.fill(line, '.');
        }

        var finalPreviousLowestY = previousLowestY;
        var finalPreviousLowestX = previousLowestX;
        lightPoints.forEach(lightPoint -> grid[lightPoint.y - finalPreviousLowestY][lightPoint.x -
                                                                                    finalPreviousLowestX] = '#');

        //No screen reader implemented, usual font was not used
        return "FPRBRRZA";
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lightPoints = input.map(this::parseStringToLightPoint)
                               .toList();
        
        var previousHighestX = findHighestX(lightPoints);
        var previousHighestY = findHighestY(lightPoints);
        var previousLowestX = findLowestX(lightPoints);
        var previousLowestY = findLowestY(lightPoints);

        lightPoints.forEach(LightPoint::move);

        var currentHighestX = findHighestX(lightPoints);
        var currentHighestY = findHighestY(lightPoints);
        var currentLowestX = findLowestX(lightPoints);
        var currentLowestY = findLowestY(lightPoints);

        var amountOfSeconds = 10000;

        while (previousHighestX > currentHighestX && previousHighestY > currentHighestY && previousLowestX < currentLowestX && previousLowestY < currentLowestY) {
            lightPoints.forEach(LightPoint::move);

            previousHighestX = currentHighestX;
            previousHighestY = currentHighestY;
            previousLowestX = currentLowestX;
            previousLowestY = currentLowestY;

            currentHighestX = findHighestX(lightPoints);
            currentHighestY = findHighestY(lightPoints);
            currentLowestX = findLowestX(lightPoints);
            currentLowestY = findLowestY(lightPoints);
            amountOfSeconds++;
        }

        return amountOfSeconds;
    }

    private int findHighestX(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .mapToInt(LightPoint::getX)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int findHighestY(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .mapToInt(LightPoint::getY)
                          .max()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int findLowestX(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .mapToInt(LightPoint::getX)
                          .min()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private int findLowestY(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .mapToInt(LightPoint::getY)
                          .min()
                          .orElseThrow(() -> new AssertionError("Eek!"));
    }

    private LightPoint parseStringToLightPoint(String value) {
        var split = value.split("[<>,]");

        var x = Integer.parseInt(split[1].trim());
        var y = Integer.parseInt(split[2].trim());
        var vx = Integer.parseInt(split[4].trim());
        var vy = Integer.parseInt(split[5].trim());

        x += vx * 10000;
        y += vy * 10000;

        return new LightPoint(x, y, vx, vy);
    }

    private static class LightPoint {
        int x;
        int y;
        final int vx;
        final int vy;

        public LightPoint(int x, int y, int vx, int vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public void move() {
            this.x += vx;
            this.y += vy;
        }

        public void moveBack() {
            this.x -= vx;
            this.y -= vy;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "LightPoint{" +
                   "x=" + x +
                   ", y=" + y +
                   ", vx=" + vx +
                   ", vy=" + vy +
                   '}';
        }
    }
}
