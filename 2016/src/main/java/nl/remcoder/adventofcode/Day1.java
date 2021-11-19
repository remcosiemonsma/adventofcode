package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day1 {
    public int handlePart1(Stream<String> input) {
        String[] steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        Position position = new Position();

        for (String step : steps) {
            walk(position, step);
        }

        return Math.abs(position.posx) + Math.abs(position.posy);
    }

    public int handlePart2(Stream<String> input) {
        String[] steps = input.findFirst()
                              .map(s -> s.split(", "))
                              .orElse(new String[0]);

        Set<Position> visitedPositions = new HashSet<>();

        Position position = new Position();
        visitedPositions.add(position.clone());

        outer:
        for (String step : steps) {
            switch (step.charAt(0)) {
                case 'R':
                    position.direction = rotateClockWise(position.direction);
                    break;
                case 'L':
                    position.direction = rotateCounterCLockWise(position.direction);
                    break;
            }
            int distance = Integer.parseInt(step.substring(1));
            switch (position.direction) {
                case NORTH:
                    for (int stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posy++;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                    break;
                case EAST:
                    for (int stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posx++;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                    break;
                case SOUTH:
                    for (int stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posy--;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                    break;
                case WEST:
                    for (int stepcounter = 0; stepcounter < distance; stepcounter++) {
                        position.posx--;
                        if (!visitedPositions.add(position.clone())) {
                            break outer;
                        }
                    }
                    break;
            }
        }

        return Math.abs(position.posx) + Math.abs(position.posy);
    }

    private void walk(Position position, String step) {
        switch (step.charAt(0)) {
            case 'R':
                position.direction = rotateClockWise(position.direction);
                break;
            case 'L':
                position.direction = rotateCounterCLockWise(position.direction);
                break;
        }
        int distance = Integer.parseInt(step.substring(1));
        switch (position.direction) {
            case NORTH:
                position.posy += distance;
                break;
            case EAST:
                position.posx += distance;
                break;
            case SOUTH:
                position.posy -= distance;
                break;
            case WEST:
                position.posx -= distance;
                break;
        }
    }

    private Direction rotateCounterCLockWise(Direction direction) {
        switch (direction) {
            case NORTH:
                direction = Direction.WEST;
                break;
            case EAST:
                direction = Direction.NORTH;
                break;
            case SOUTH:
                direction = Direction.EAST;
                break;
            case WEST:
                direction = Direction.SOUTH;
                break;
        }
        return direction;
    }

    private Direction rotateClockWise(Direction direction) {
        switch (direction) {
            case NORTH:
                direction = Direction.EAST;
                break;
            case EAST:
                direction = Direction.SOUTH;
                break;
            case SOUTH:
                direction = Direction.WEST;
                break;
            case WEST:
                direction = Direction.NORTH;
                break;
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
