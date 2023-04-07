package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day10 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var asteroids = parseAsteroids(input);

        return asteroids.stream()
                        .mapToInt(asteroid -> (int) asteroids.stream()
                                                             .filter(Predicate.not(asteroid::equals))
                                                             .map(asteroid::calculateAngle)
                                                             .distinct()
                                                             .count())
                        .max()
                        .orElseThrow(AssertionError::new);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var asteroids = parseAsteroids(input);

        var monitoringStation = asteroids.stream()
                                         .max(Comparator.comparing(asteroid -> asteroids.stream()
                                                                                        .filter(Predicate.not(
                                                                                                asteroid::equals))
                                                                                        .map(asteroid::calculateAngle)
                                                                                        .distinct()
                                                                                        .count()))
                                         .orElseThrow(AssertionError::new);

        asteroids.remove(monitoringStation);

        asteroids.forEach(asteroid -> asteroid.setDistanceToStation(asteroid.calculateDistance(monitoringStation)));
        asteroids.forEach(asteroid -> asteroid.setAngleWithStation(asteroid.calculateAngle(monitoringStation)));

        var sortedAsteroids = asteroids.stream()
                                       .sorted(Comparator.comparing(Asteroid::getDistanceToStation))
                                       .collect(Collectors.groupingBy(Asteroid::getAngleWithStation,
                                                                      Collectors.toCollection(ArrayList::new)));

        var angles = sortedAsteroids.keySet()
                                    .stream()
                                    .sorted()
                                    .toArray(Double[]::new);

        var destroyedAsteroids = new ArrayList<Asteroid>();

        var angleCounter = 0;

        while (destroyedAsteroids.size() < 200) {
            var lineOfAsteroids = sortedAsteroids.get(angles[angleCounter++]);

            if (!lineOfAsteroids.isEmpty()) {
                Asteroid destroyedAsteroid = lineOfAsteroids.remove(0);
                destroyedAsteroids.add(destroyedAsteroid);
            }
            if (angleCounter >= angles.length) {
                angleCounter = 0;
            }
        }

        var lastDestroyedAsteroid = destroyedAsteroids.get(199);
        
        return (lastDestroyedAsteroid.x * 100) + lastDestroyedAsteroid.y;
    }

    private List<Asteroid> parseAsteroids(Stream<String> input) {
        var asteroids = new ArrayList<Asteroid>();

        var grid = input.toList();

        for (var y = 0; y < grid.size(); y++) {
            var line = grid.get(y);
            for (var x = 0; x < line.length(); x++) {
                char c = line.charAt(x);

                if (c == '#') {
                    asteroids.add(new Asteroid(x, y));
                }
            }
        }
        return asteroids;
    }

    public static class Asteroid {
        private final int x;
        private final int y;
        private double distanceToStation;
        private double angleWithStation;

        Asteroid(int x, int y) {
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

        double calculateDistance(Asteroid other) {
            return Math.sqrt(Math.pow(y - other.y, 2) + Math.pow(x - other.x, 2));
        }

        double calculateAngle(Asteroid other) {
            double angle = Math.toDegrees(Math.atan2(other.y - y, other.x - x)) - 90;

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
            Asteroid asteroid = (Asteroid) o;
            return x == asteroid.x &&
                   y == asteroid.y;
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
