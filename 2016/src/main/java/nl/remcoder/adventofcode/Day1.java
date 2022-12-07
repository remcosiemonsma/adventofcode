package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        var steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        var position = new Position();

        for (var step : steps) {
            walk(position, step);
        }

        return Math.abs(position.posx) + Math.abs(position.posy);
    }

    public int handlePart2(Stream<String> input) {
        var steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        var visitedPositions = new HashSet<>();

        var position = new Position();
        visitedPositions.add(position.clone());

        outer:
        for (String step : steps) {
            switch (step.charAt(0)) {
                case 'R' -> position.direction = rotateClockWise(position.direction);
                case 'L' -> position.direction = rotateCounterCLockWise(position.direction);
            }
            int distance = Integer.parseInt(step.substring(1));
            switch (position.direction) {
                case NORTH -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posy++;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                }
                case EAST -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posx++;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                }
                case SOUTH -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posy--;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                }
                case WEST -> {
                    for (var stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posx--;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                }
            }
        }

        return Math.abs(position.posx) + Math.abs(position.posy);
    }

    private void walk(Position position, String step) {
        switch (step.charAt(0)) {
            case 'R' -> position.direction = rotateClockWise(position.direction);
            case 'L' -> position.direction = rotateCounterCLockWise(position.direction);
        }
        var distance = Integer.parseInt(step.substring(1));
        switch (position.direction) {
            case NORTH -> position.posy += distance;
            case EAST -> position.posx += distance;
            case SOUTH -> position.posy -= distance;
            case WEST -> position.posx -= distance;
        }
    }

    private Direction rotateCounterCLockWise(Direction direction) {
        switch (direction) {
            case NORTH -> direction = Direction.WEST;
            case EAST -> direction = Direction.NORTH;
            case SOUTH -> direction = Direction.EAST;
            case WEST -> direction = Direction.SOUTH;
        }
        return direction;
    }

    private Direction rotateClockWise(Direction direction) {
        switch (direction) {
            case NORTH -> direction = Direction.EAST;
            case EAST -> direction = Direction.SOUTH;
            case SOUTH -> direction = Direction.WEST;
            case WEST -> direction = Direction.NORTH;
        }
        return direction;
    }

    private static class Position implements Cloneable {
        int posx = 0;
        int posy = 0;
        Direction direction = Direction.NORTH;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return posx == position.posx && posy == position.posy;
        }

        @Override
        public int hashCode() {
            return Objects.hash(posx, posy);
        }

        @Override
        public Position clone() {
            try {
                return (Position) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }

    private enum Direction {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }
}
