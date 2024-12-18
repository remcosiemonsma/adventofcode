package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;

import java.util.ArrayList;
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

        coordinates.add(Coordinate.ORIGIN);

        var data = new DataContainer();

        input.map(digStepMapper)
             .forEach(digStep -> {
                 var currentLocation = coordinates.getLast();

                 var newLocation = switch (digStep.direction()) {
                     case UP -> new Coordinate(currentLocation.x(), currentLocation.y() - digStep.distance());
                     case LEFT -> new Coordinate(currentLocation.x() - digStep.distance(), currentLocation.y());
                     case DOWN -> new Coordinate(currentLocation.x(), currentLocation.y() + digStep.distance());
                     case RIGHT -> new Coordinate(currentLocation.x() + digStep.distance(), currentLocation.y());
                     case UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT -> throw new AssertionError("Not supposed to happen!");
                 };

                 data.setBorderSize(data.borderSize() + currentLocation.getDistanceTo(newLocation));

                 var areaToAdd = 0L;

                 areaToAdd += (long) currentLocation.x() * (long) newLocation.y();
                 areaToAdd -= (long) currentLocation.y() * (long) newLocation.x();

                 data.setArea(data.area() + areaToAdd);

                 coordinates.add(newLocation);
             });

        var first = coordinates.getFirst();
        var last = coordinates.getLast();

        data.setBorderSize(data.borderSize() + first.getDistanceTo(last));

        var areaToAdd = 0L;

        areaToAdd += (long) last.x() * (long) first.y();
        areaToAdd -= (long) last.y() * (long) first.x();

        data.setArea(data.area() + areaToAdd);

        return (Math.abs(data.area()) / 2) + (data.borderSize() / 2) + 1;
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

    private static class DataContainer {
        private long area;
        private long borderSize;

        public long area() {
            return area;
        }

        public void setArea(long area) {
            this.area = area;
        }

        public long borderSize() {
            return borderSize;
        }

        public void setBorderSize(long borderSize) {
            this.borderSize = borderSize;
        }
    }

    private record DigStep(Direction direction, int distance) {
    }
}
