package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Long> {
    @Override
    public Long handlePart1(Stream<String> input) {
        return calculateLavaSize(input, this::mapDigStep).longValue();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        return calculateLavaSize(input, this::mapDigStep2).longValue();
    }

    public BigInteger handlePart2BigInteger(Stream<String> input) {
        return calculateLavaSize(input, this::mapDigStep2);
    }

    private BigInteger calculateLavaSize(Stream<String> input, Function<String, DigStep> digStepMapper) {
        var coordinates = new ArrayList<Coordinate>();

        coordinates.add(new Coordinate(0, 0));

        var data = new DataContainer();

        input.map(digStepMapper)
             .forEach(digStep -> {
                 var currentLocation = coordinates.getLast();

                 var newLocation = switch (digStep.direction()) {
                     case UP -> new Coordinate(currentLocation.x(), currentLocation.y() - digStep.distance());
                     case LEFT -> new Coordinate(currentLocation.x() - digStep.distance(), currentLocation.y());
                     case DOWN -> new Coordinate(currentLocation.x(), currentLocation.y() + digStep.distance());
                     case RIGHT -> new Coordinate(currentLocation.x() + digStep.distance(), currentLocation.y());
                 };

                 data.setBorderSize(data.borderSize().add(currentLocation.getDistanceToBigInteger(newLocation)));

                 var areaToAdd = BigInteger.valueOf(currentLocation.x()).multiply(BigInteger.valueOf(newLocation.y()));
                 areaToAdd = areaToAdd.subtract(BigInteger.valueOf(currentLocation.y()).multiply(BigInteger.valueOf(newLocation.x())));

                 data.setArea(data.area().add(areaToAdd));

                 coordinates.add(newLocation);
             });

        var first = coordinates.getFirst();
        var last = coordinates.getLast();

        data.setBorderSize(data.borderSize().add(BigInteger.valueOf(first.getDistanceTo(last))));

        var areaToAdd = BigInteger.valueOf(last.x()).multiply(BigInteger.valueOf(first.y()));
        areaToAdd = areaToAdd.subtract(BigInteger.valueOf(last.y()).multiply(BigInteger.valueOf(first.x())));

        data.setArea(data.area().add(areaToAdd));

        return data.area().divide(BigInteger.TWO).add(data.borderSize().divide(BigInteger.TWO)).add(BigInteger.ONE);
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

        return new DigStep(direction, Long.parseLong(hexData.substring(0, 5), 16));
    }

    private static class DataContainer {
        private BigInteger area = BigInteger.ZERO;
        private BigInteger borderSize = BigInteger.ZERO;

        public BigInteger area() {
            return area;
        }

        public void setArea(BigInteger area) {
            this.area = area;
        }

        public BigInteger borderSize() {
            return borderSize;
        }

        public void setBorderSize(BigInteger borderSize) {
            this.borderSize = borderSize;
        }
    }

    private record DigStep(Direction direction, long distance) {
    }
}
