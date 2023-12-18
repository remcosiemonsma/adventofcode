package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return calculateLavaSize(input, this::mapDigStep);
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return calculateLavaSize(input, this::mapDigStep2);
    }

    private long calculateLavaSize(Stream<String> input, Function<String, DigStep> digStepMapper) {
        var coordinates = new ArrayList<Coordinate>();

        coordinates.add(new Coordinate(0, 0));

        input.map(digStepMapper)
             .forEach(digStep -> {
                 var currentLocation = coordinates.getLast();

                 var newLocation = switch (digStep.direction()) {
                     case UP -> new Coordinate(currentLocation.x(), currentLocation.y() - digStep.distance());
                     case LEFT -> new Coordinate(currentLocation.x() - digStep.distance(), currentLocation.y());
                     case DOWN -> new Coordinate(currentLocation.x(), currentLocation.y() + digStep.distance());
                     case RIGHT -> new Coordinate(currentLocation.x() + digStep.distance(), currentLocation.y());
                 };

                 coordinates.add(newLocation);
             });

        var area = calculateArea(coordinates);

        return area + (countBorderPoints(coordinates) / 2) + 1;
    }

    private long countBorderPoints(List<Coordinate> coordinates) {
        var borderCount = 0;

        for (var i = 0; i < coordinates.size(); i++) {
            var first = coordinates.get(i);
            var second = coordinates.get((i + 1) % coordinates.size());

            borderCount += first.getDistanceTo(second);
        }

        return borderCount;
    }

    private long calculateArea(List<Coordinate> coordinates) {
        var area = 0d;

        for (var i = 0; i < coordinates.size(); i++) {
            var j = (i + 1) % coordinates.size();

            area += (long) coordinates.get(i).x() * (long) coordinates.get(j).y();
            area -= (long) coordinates.get(i).y() * (long) coordinates.get(j).x();
        }

        area /= 2;
        return (long) area;
    }

    private DigStep mapDigStep(String line) {
        var split = line.split(" ");

        var direction = switch (split[0]) {
            case "R" -> Direction.RIGHT;
            case "L" -> Direction.LEFT;
            case "U" -> Direction.UP;
            case "D" -> Direction.DOWN;
            default -> throw new AssertionError("Eek!");
        };

        return new DigStep(direction, Integer.parseInt(split[1]));
    }

    private DigStep mapDigStep2(String line) {
        var hexData = line.split(" \\(#")[1].substring(0, 7);

        var direction = switch (hexData.charAt(5)) {
            case '0' -> Direction.RIGHT;
            case '2' -> Direction.LEFT;
            case '3' -> Direction.UP;
            case '1' -> Direction.DOWN;
            default -> throw new AssertionError("Eek!");
        };

        return new DigStep(direction, Integer.parseInt(hexData.substring(0, 5), 16));
    }

    private record DigStep(Direction direction, int distance) {
    }
}
