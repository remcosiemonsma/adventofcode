package nl.remcoder.adventofcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

public class Day3 {
    public int handlePart1(Stream<String> input) {
        String line = input.findFirst().get();

        Set<Position> positions = new HashSet<>();

        Position position = new Position(0, 0);

        positions.add(position);

        for (char c : line.toCharArray()) {
            position = move(position, c);

            positions.add(position);
        }
        
        return positions.size();
    }

    public int handlePart2(Stream<String> input) {
        String line = input.findFirst().get();

        Set<Position> positions = new HashSet<>();

        Position positionSanta = new Position(0, 0);
        Position positionRoboSanta = new Position(0, 0);

        positions.add(positionSanta);

        char[] charArray = line.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (i % 2 == 0) {
                positionSanta = move(positionSanta, c);

                positions.add(positionSanta);
            } else {
                positionRoboSanta = move(positionRoboSanta, c);

                positions.add(positionRoboSanta);
            }

        }
        
        return positions.size();
    }

    private Position move(Position position, char c) {
        switch (c) {
            case '^':
                position = new Position(position.x + 1, position.y);
                break;
            case 'v':
                position = new Position(position.x - 1, position.y);
                break;
            case '>':
                position = new Position(position.x, position.y + 1);
                break;
            case '<':
                position = new Position(position.x, position.y - 1);
                break;
        }
        return position;
    }

    private static class Position {
        private final int x;
        private final int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
