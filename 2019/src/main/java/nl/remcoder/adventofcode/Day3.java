package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day3 implements AdventOfCodeSolution<Integer> {
    public Integer handlePart1(Stream<String> input) {
        var lines = input.toList();

        var line1 = lines.get(0);
        var line2 = lines.get(1);

        var path1 = parsePath(line1.split(","));
        var path2 = parsePath(line2.split(","));

        var start = new Coordinate(0, 0);

        return path1.stream()
                    .filter(path2::contains)
                    .mapToInt(other -> start.getDistanceTo(other.coordinate()))
                    .min()
                    .orElseThrow(() -> new AssertionError("Eek!"));
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();

        var line1 = lines.get(0);
        var line2 = lines.get(1);

        var path1 = parsePath(line1.split(","));
        var path2 = parsePath(line2.split(","));

        var path1Intersections = path1.stream()
                                      .filter(path2::contains)
                                      .toList();
        var path2Intersections = path2.stream()
                                      .filter(path1::contains)
                                      .toList();
        
        var minimalPath = Integer.MAX_VALUE;
        
        for (var intersection : path1Intersections) {
            for (var otherInsection : path2Intersections) {
                if (intersection.equals(otherInsection)) {
                    var pathLength = intersection.steps + otherInsection.steps;
                    if (pathLength < minimalPath) {
                        minimalPath = pathLength;
                    }
                }
            }
        }

        return minimalPath;
    }

    private Set<PositionWithSteps> parsePath(String[] path) {
        var currentPosition = new PositionWithSteps(new Coordinate(0, 0), 0);

        var coordinates = new HashSet<PositionWithSteps>();

        for (var segment : path) {
            var distance = Integer.parseInt(segment.substring(1));
            switch (segment.charAt(0)) {
                case 'D' -> currentPosition = fillNextSegment(Direction.DOWN, distance, coordinates, currentPosition);
                case 'U' -> currentPosition = fillNextSegment(Direction.UP, distance, coordinates, currentPosition);
                case 'L' -> currentPosition = fillNextSegment(Direction.LEFT, distance, coordinates, currentPosition);
                case 'R' -> currentPosition = fillNextSegment(Direction.RIGHT, distance, coordinates, currentPosition);
            }
        }

        return coordinates;
    }

    private PositionWithSteps fillNextSegment(Direction direction, int distance, Set<PositionWithSteps> coordinates,
                                              PositionWithSteps currentPosition) {
        for (var step = 0; step < distance; step++) {
            currentPosition = new PositionWithSteps(currentPosition.coordinate().getNeighbor(direction),
                                                    currentPosition.steps + 1);
            coordinates.add(currentPosition);
        }
        return currentPosition;
    }

    private record PositionWithSteps(Coordinate coordinate, int steps) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            PositionWithSteps that = (PositionWithSteps) o;
            return Objects.equals(coordinate, that.coordinate);
        }

        @Override
        public int hashCode() {
            return Objects.hash(coordinate);
        }
    }
}
