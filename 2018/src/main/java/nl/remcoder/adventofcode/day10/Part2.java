package nl.remcoder.adventofcode.day10;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Part2 {
    public static void main(String[] args) throws Exception {
        List<LightPoint> lightPoints = Files.lines(Paths.get(ClassLoader.getSystemResource("day10/input").toURI()))
                                        .map(Part2::parseStringToLightPoint)
                                        .collect(Collectors.toList());

        int previousHighestX = findHighestX(lightPoints);
        int previousHighestY = findHighestY(lightPoints);
        int previousLowestX = findLowestX(lightPoints);
        int previousLowestY = findLowestY(lightPoints);

        lightPoints.forEach(LightPoint::move);

        int currentHighestX = findHighestX(lightPoints);
        int currentHighestY = findHighestY(lightPoints);
        int currentLowestX = findLowestX(lightPoints);
        int currentLowestY = findLowestY(lightPoints);

        int amountOfSeconds = 10000;

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

        lightPoints.forEach(LightPoint::moveBack);

        int xdiff = previousHighestX - previousLowestX;
        int ydiff = previousHighestY - previousLowestY;

        char[][] grid = new char[ydiff + 1][xdiff + 1];

        for (char[] line : grid) {
            Arrays.fill(line, '.');
        }

        int finalPreviousLowestY = previousLowestY;
        int finalPreviousLowestX = previousLowestX;
        lightPoints.forEach(lightPoint -> grid[lightPoint.y - finalPreviousLowestY][lightPoint.x -
                                                                                        finalPreviousLowestX] = '#');

        for (char[] line : grid) {
            System.out.println(Arrays.toString(line));
        }

        System.out.println(amountOfSeconds);
    }

    private static int findHighestX(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .max(Comparator.comparing(lightPoint -> lightPoint.x))
                          .get().x;
    }

    private static int findHighestY(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .max(Comparator.comparing(lightPoint -> lightPoint.y))
                          .get().y;
    }

    private static int findLowestX(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .min(Comparator.comparing(lightPoint -> lightPoint.x))
                          .get().x;
    }

    private static int findLowestY(List<LightPoint> lightPoints) {
        return lightPoints.stream()
                          .min(Comparator.comparing(lightPoint -> lightPoint.y))
                          .get().y;
    }

    private static LightPoint parseStringToLightPoint(String value) {
        String[] split = value.split("[<>,]");

        int x = Integer.parseInt(split[1].trim());
        int y = Integer.parseInt(split[2].trim());
        int vx = Integer.parseInt(split[4].trim());
        int vy = Integer.parseInt(split[5].trim());

        x += vx * 10000;
        y += vy * 10000;

        LightPoint lightPoint = new LightPoint(x, y, vx, vy);

        return lightPoint;
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
