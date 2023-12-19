package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
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
        var coordinates = new ArrayList<CoordinateBigInteger>();

        coordinates.add(new CoordinateBigInteger(BigInteger.ZERO, BigInteger.ZERO));

        var data = new DataContainer();

        input.map(digStepMapper)
             .forEach(digStep -> {
                 var currentLocation = coordinates.getLast();

                 var newLocation = switch (digStep.direction()) {
                     case UP -> new CoordinateBigInteger(currentLocation.x(), currentLocation.y().subtract(BigInteger.valueOf(digStep.distance())));
                     case LEFT -> new CoordinateBigInteger(currentLocation.x().subtract(BigInteger.valueOf(digStep.distance())), currentLocation.y());
                     case DOWN -> new CoordinateBigInteger(currentLocation.x(), currentLocation.y().add(BigInteger.valueOf(digStep.distance())));
                     case RIGHT -> new CoordinateBigInteger(currentLocation.x().add(BigInteger.valueOf(digStep.distance())), currentLocation.y());
                 };

                 data.setBorderSize(data.borderSize().add(currentLocation.getDistanceToBigInteger(newLocation)));

                 var areaToAdd = currentLocation.x().multiply(newLocation.y());
                 areaToAdd = areaToAdd.subtract(currentLocation.y().multiply(newLocation.x()));

                 data.setArea(data.area().add(areaToAdd));

                 coordinates.add(newLocation);
             });

        var first = coordinates.getFirst();
        var last = coordinates.getLast();

        data.setBorderSize(data.borderSize().add(first.getDistanceToBigInteger(last)));

        var areaToAdd = last.x().multiply(first.y());
        areaToAdd = areaToAdd.subtract(last.y().multiply(first.x()));

        data.setArea(data.area().add(areaToAdd));

        return data.area().abs().divide(BigInteger.TWO).add(data.borderSize().divide(BigInteger.TWO)).add(BigInteger.ONE);
    }

    private DigStep mapDigStep(String line) {
        var split = line.split(" ");

        var direction = switch (split[0]) {
            case "R" -> Direction.RIGHT;
            case "D" -> Direction.DOWN;
            case "L" -> Direction.LEFT;
            case "U" -> Direction.UP;
            default -> throw new AssertionError("Eek!");
        };

        return new DigStep(direction, Integer.parseInt(split[1]));
    }

    private DigStep mapDigStep2(String line) {
        var hexData = line.split(" \\(#")[1].substring(0, 7);

        var direction = switch (hexData.charAt(5)) {
            case '0' -> Direction.RIGHT;
            case '1' -> Direction.DOWN;
            case '2' -> Direction.LEFT;
            case '3' -> Direction.UP;
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

    private record CoordinateBigInteger(BigInteger x, BigInteger y) {
        public BigInteger getDistanceToBigInteger(CoordinateBigInteger other) {
            return x.subtract(other.x).abs().add(y.subtract(other.y).abs());
        }
    }
}
