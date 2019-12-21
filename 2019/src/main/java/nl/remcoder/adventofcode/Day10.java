package nl.remcoder.adventofcode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 {
    public long handlePart1(Stream<String> input) {
        List<Point> asteroids = new ArrayList<>();

        List<String> grid = input.collect(Collectors.toList());

        for (int y = 0; y < grid.size(); y++) {
            String line = grid.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c == '#') {
                    Point asteroid = new Point(x, y);
                    asteroids.add(asteroid);
                }
            }
        }

        return asteroids.stream()
                        .map(asteroid -> asteroids.stream()
                                                  .filter(otherasteroid -> !asteroid
                                                          .equals(otherasteroid))
                                                  .map(asteroid::calculateAngle)
                                                  .distinct()
                                                  .count())
                        .max(Long::compareTo)
                        .orElseThrow(AssertionError::new);
    }

    public int handlePart2(Stream<String> input) {
        List<Point> asteroids = new ArrayList<>();

        List<String> grid = input.collect(Collectors.toList());

        for (int y = 0; y < grid.size(); y++) {
            String line = grid.get(y);
            for (int x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c == '#') {
                    Point asteroid = new Point(x, y);
                    asteroids.add(asteroid);
                }
            }
        }

        Point monitoringStation = asteroids.stream()
                                           .max(Comparator.comparing(asteroid -> asteroids.stream()
                                                                                          .filter(otherasteroid -> !asteroid
                                                                                                  .equals(otherasteroid))
                                                                                          .map(asteroid::calculateAngle)
                                                                                          .distinct()
                                                                                          .count()))
                                           .orElseThrow(AssertionError::new);

        asteroids.remove(monitoringStation);

        asteroids.forEach(asteroid -> asteroid.setDistanceToStation(asteroid.calculateDistance(monitoringStation)));
        asteroids.forEach(asteroid -> asteroid.setAngleWithStation(asteroid.calculateAngle(monitoringStation)));

        Map<Double, ArrayList<Point>> sortedAsteroids = asteroids.stream()
                                                                 .sorted(Comparator.comparing(
                                                                         Point::getDistanceToStation))
                                                                 .collect(Collectors.groupingBy(
                                                                         Point::getAngleWithStation,
                                                                         Collectors.toCollection(
                                                                                 ArrayList::new)));

        Double[] angles = sortedAsteroids.keySet()
                                         .stream()
                                         .sorted()
                                         .toArray(Double[]::new);

        List<Point> destroyedAsteroids = new ArrayList<>();

        int angleCounter = 0;

        while (destroyedAsteroids.size() < 200) {
            List<Point> lineOfAsteroids = sortedAsteroids.get(angles[angleCounter++]);

            if (!lineOfAsteroids.isEmpty()) {
                Point destroyedAsteroid = lineOfAsteroids.remove(0);
                destroyedAsteroids.add(destroyedAsteroid);
            }
            if (angleCounter >= angles.length) {
                angleCounter = 0;
            }
        }

        return (destroyedAsteroids.get(199).x * 100) + destroyedAsteroids.get(199).y;
    }

    public static class Point {
        private final int x;
        private final int y;
        private double distanceToStation;
        private double angleWithStation;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public double getDistanceToStation() {
            return distanceToStation;
        }

        public void setDistanceToStation(double distanceToStation) {
            this.distanceToStation = distanceToStation;
        }

        public double getAngleWithStation() {
            return angleWithStation;
        }

        public void setAngleWithStation(double angleWithStation) {
            this.angleWithStation = angleWithStation;
        }

        double calculateDistance(Point point) {
            return Math.sqrt(Math.pow(y - point.y, 2) + Math.pow(x - point.x, 2));
        }

        double calculateAngle(Point point) {
            double angle = Math.toDegrees(Math.atan2(point.y - y, point.x - x)) - 90;

            if (angle < 0) {
                angle += 360;
            }

            return angle;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Point point = (Point) o;
            return x == point.x &&
                   y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point{" +
                   "x=" + x +
                   ", y=" + y +
                   ", distanceToStation=" + distanceToStation +
                   ", angleWithStation=" + angleWithStation +
                   '}';
        }
    }
}
