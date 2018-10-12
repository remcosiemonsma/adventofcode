package nl.remcoder.adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Part1 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI())).get(0);

        Map<Position, Integer> positionCounter = new HashMap<>();

        Position position = new Position(0, 0);

        positionCounter.put(position, 1);

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

            if (!positionCounter.containsKey(position)) {
                positionCounter.put(position, 0);
            }

            positionCounter.put(position, positionCounter.get(position) + 1);
        }

        System.out.println(positionCounter.size());
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
