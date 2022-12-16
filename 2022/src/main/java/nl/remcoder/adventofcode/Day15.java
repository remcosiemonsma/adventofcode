package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day15 implements AdventOfCodeSolution<Long> {
    private static final Pattern SENSOR_PATTERN =
            Pattern.compile("Sensor at x=(-?\\d*), y=(-?\\d*): closest beacon is at x=(-?\\d*), y=(-?\\d*)");
    public int rowToExamine = 2000000;

    @Override
    public Long handlePart1(Stream<String> input) {
        var sensors = input.map(SENSOR_PATTERN::matcher)
                           .filter(Matcher::matches)
                           .map(this::mapToSensor)
                           .filter(sensor -> sensor.isRowInRange(rowToExamine))
                           .toList();

        return sensors.stream()
                      .map(sensor -> sensor.getCoverageInRow(rowToExamine))
                      .flatMap(Collection::stream)
                      .distinct()
                      .filter(coordinate -> sensors.stream().map(Sensor::getBeacon)
                                                   .noneMatch(beacon -> beacon.equals(coordinate)))
                      .count();
    }

    @Override
    public Long handlePart2(Stream<String> input) {
        var sensors = input.map(SENSOR_PATTERN::matcher)
                           .filter(Matcher::matches)
                           .map(this::mapToSensor)
                           .toList();

        int expectedRows = rowToExamine * 2;

        for (var row = expectedRows; row >= 0; row--) {
            for (Sensor sensor : sensors) {
                Coordinate left = sensor.getLeftOutsideRangeCoordinateOnRow(row);
                Coordinate right = sensor.getRightOutsideRangeCoordinateOnRow(row);

                if (left != null) {
                    if (left.x() >= 0) {
                        if (sensors.stream().noneMatch(sensor1 -> sensor1.isCoordinateInRange(left))) {
                            return (left.x() * 4000000L) + left.y();
                        }
                    }
                }
                if (right != null) {
                    if (right.x() <= expectedRows) {
                        if (sensors.stream().noneMatch(sensor1 -> sensor1.isCoordinateInRange(right))) {
                            return (right.x() * 4000000L) + right.y();
                        }
                    }
                }
            }
        }

        return null;
    }

    private Sensor mapToSensor(Matcher matcher) {
        return new Sensor(new Coordinate(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2))),
                          new Coordinate(Integer.parseInt(matcher.group(3)), Integer.parseInt(matcher.group(4))));
    }

    private static class Sensor {
        private final Coordinate position;
        private final Coordinate beacon;
        private final int range;

        private Sensor(Coordinate position, Coordinate beacon) {
            this.position = position;
            this.beacon = beacon;
            range = position.getDistanceTo(beacon);
        }

        public Coordinate getBeacon() {
            return beacon;
        }

        public boolean isRowInRange(int row) {
            int minRow = position.y() - range;
            int maxRow = position.y() + range;

            return minRow <= row && row <= maxRow;
        }

        public List<Coordinate> getCoverageInRow(int row) {
            int distanceToRow = Math.abs(position.y() - row);
            int rangeInRow = range - distanceToRow;

            var coordinates = new ArrayList<Coordinate>();

            for (int x = position.x() - rangeInRow; x <= position.x() + rangeInRow; x++) {
                coordinates.add(new Coordinate(x, row));
            }

            return coordinates;
        }

        public Coordinate getLeftOutsideRangeCoordinateOnRow(int row) {
            int distanceToRow = Math.abs(position.y() - row);
            int rangeInRow = range - distanceToRow;
            
            if (rangeInRow >= 0) {
                return new Coordinate(position.x() - (rangeInRow + 1), row);
            } else {
                return null;
            }
        }
        
        public Coordinate getRightOutsideRangeCoordinateOnRow(int row) {
            int distanceToRow = Math.abs(position.y() - row);
            int rangeInRow = range - distanceToRow;
            
            if (rangeInRow >= 0) {
                return new Coordinate(position.x() + (rangeInRow + 1), row);
            } else {
                return null;
            }
        }
        
        public boolean isCoordinateInRange(Coordinate coordinate) {
            return position.getDistanceTo(coordinate) <= range;
        }
    }
}
