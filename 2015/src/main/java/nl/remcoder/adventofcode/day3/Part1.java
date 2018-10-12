package nl.remcoder.adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI())).get(0);

        Set<Position> positions = new HashSet<>();

        Position position = new Position(0, 0);

        positions.add(position);

        for (char c : line.toCharArray()) {
            switch (c) {
                case '^':
                    position = new Position(position.getX() + 1, position.getY());
                    break;
                case 'v':
                    position = new Position(position.getX() - 1, position.getY());
                    break;
                case '>':
                    position = new Position(position.getX(), position.getY() + 1);
                    break;
                case '<':
                    position = new Position(position.getX(), position.getY() - 1);
                    break;
            }

            positions.add(position);
        }

        System.out.println(positions.size());
    }

    private static class Position {
        private int x;
        private int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
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

            if (x != position.x) {
                return false;
            }
            return y == position.y;
        }

        @Override
        public int hashCode() {
            int result = x;
            result = 31 * result + y;
            return result;
        }
    }
}
