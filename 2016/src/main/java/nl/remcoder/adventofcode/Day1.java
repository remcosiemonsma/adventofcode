package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;
import nl.remcoder.adventofcode.library.model.Coordinate;
import nl.remcoder.adventofcode.library.model.Direction;
import nl.remcoder.adventofcode.library.model.Vector;

import java.util.HashSet;
import java.util.stream.Stream;

public class Day1 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        var position = new Vector(Coordinate.ORIGIN, Direction.UP);

        for (var step : steps) {
            position = walk(position, step);
        }

        return position.coordinate().getDistanceTo(Coordinate.ORIGIN);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        var visitedPositions = new HashSet<>();

        var position = new Vector(Coordinate.ORIGIN, Direction.UP);
        visitedPositions.add(position.coordinate());

        outer:
        for (String step : steps) {
            var direction = position.direction();
            direction = switch (step.charAt(0)) {
                case 'R' -> direction.rotateClockWise();
                case 'L' -> direction.rotateCounterClockWise();
                default -> throw new AssertionError("Eek!");
            };
            int distance = Integer.parseInt(step.substring(1));
            switch (direction) {
                case UP -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position = new Vector(new Coordinate(position.coordinate().x(), 
                                                             position.coordinate().y() + 1), direction);
                        if (!visitedPositions.add(position.coordinate())) {
                            break outer;
                        }
                    }
                }
                case RIGHT -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position = new Vector(new Coordinate(position.coordinate().x() + 1,
                                                             position.coordinate().y()), direction);
                        if (!visitedPositions.add(position.coordinate())) {
                            break outer;
                        }
                    }
                }
                case DOWN -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position = new Vector(new Coordinate(position.coordinate().x(),
                                                             position.coordinate().y() - 1), direction);
                        if (!visitedPositions.add(position.coordinate())) {
                            break outer;
                        }
                    }
                }
                case LEFT -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position = new Vector(new Coordinate(position.coordinate().x() - 1,
                                                             position.coordinate().y()), direction);
                        if (!visitedPositions.add(position.coordinate())) {
                            break outer;
                        }
                    }
                }
            }
        }

        return position.coordinate().getDistanceTo(Coordinate.ORIGIN);
    }

    private Vector walk(Vector position, String step) {
        var direction = position.direction();
        direction = switch (step.charAt(0)) {
            case 'R' -> direction.rotateClockWise();
            case 'L' -> direction.rotateCounterClockWise();
            default -> throw new AssertionError("Eek!");
        };
        var distance = Integer.parseInt(step.substring(1));
        return switch (direction) {
            case UP -> new Vector(new Coordinate(position.coordinate().x(), position.coordinate().y() + distance), direction);
            case LEFT -> new Vector(new Coordinate(position.coordinate().x() + distance, position.coordinate().y()), direction);
            case DOWN -> new Vector(new Coordinate(position.coordinate().x(), position.coordinate().y() - distance), direction);
            case RIGHT -> new Vector(new Coordinate(position.coordinate().x() - distance, position.coordinate().y()), direction);
            case UPLEFT, UPRIGHT, DOWNRIGHT, DOWNLEFT -> throw new AssertionError("Not supposed to happen!");
        };
    }
}
