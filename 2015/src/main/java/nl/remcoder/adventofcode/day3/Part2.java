package nl.remcoder.adventofcode.day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

public class Part2 {
    public static void main(String[] args) throws Exception {
        String line = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day3/input").toURI())).get(0);

        Set<Position> positions = new HashSet<>();

        Position positionSanta = new Position(0, 0);
        Position positionRoboSanta = new Position(0, 0);

        positions.add(positionSanta);

        char[] charArray = line.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (i % 2 == 0) {
                switch (c) {
                    case '^':
                        positionSanta = new Position(positionSanta.getX() + 1, positionSanta.getY());
                        break;
                    case 'v':
                        positionSanta = new Position(positionSanta.getX() - 1, positionSanta.getY());
                        break;
                    case '>':
                        positionSanta = new Position(positionSanta.getX(), positionSanta.getY() + 1);
                        break;
                    case '<':
                        positionSanta = new Position(positionSanta.getX(), positionSanta.getY() - 1);
                        break;
                }

                positions.add(positionSanta);
            } else {
                switch (c) {
                    case '^':
                        positionRoboSanta = new Position(positionRoboSanta.getX() + 1, positionRoboSanta.getY());
                        break;
                    case 'v':
                        positionRoboSanta = new Position(positionRoboSanta.getX() - 1, positionRoboSanta.getY());
                        break;
                    case '>':
                        positionRoboSanta = new Position(positionRoboSanta.getX(), positionRoboSanta.getY() + 1);
                        break;
                    case '<':
                        positionRoboSanta = new Position(positionRoboSanta.getX(), positionRoboSanta.getY() - 1);
                        break;
                }

                positions.add(positionRoboSanta);
            }

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
