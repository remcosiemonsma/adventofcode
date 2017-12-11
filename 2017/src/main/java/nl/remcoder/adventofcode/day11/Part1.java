package nl.remcoder.adventofcode.day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part1 {
    public static void main(String[] args) throws Exception {
        Coordinate coordinate = new Coordinate();

        coordinate.x = 0;
        coordinate.y = 0;

        Arrays.stream(Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day11/input").toURI())).get(0).split(",")).forEach(s -> {
            switch (s) {
                case "n":
                    moveNorth(coordinate);
                    break;
                case "s":
                    moveSouth(coordinate);
                    break;
                case "nw":
                    moveNorthWest(coordinate);
                    break;
                case "ne":
                    moveNorthEast(coordinate);
                    break;
                case "sw":
                    moveSouthWest(coordinate);
                    break;
                case "se":
                    moveSouthEast(coordinate);
                    break;
            }
        });

        System.out.println(coordinate);

        int steps = 0;

        while (coordinate.x != 0 || coordinate.y != 0) {
            if (coordinate.x < 0) {
                if (coordinate.y < 0) {
                    moveSouthWest(coordinate);
                } else if (coordinate.y > 0){
                    moveNorthWest(coordinate);
                } else {
                    moveNorthWest(coordinate);
                }
            } else if (coordinate.x > 0) {
                if (coordinate.y < 0) {
                    moveSouthEast(coordinate);
                } else if (coordinate.y > 0){
                    moveNorthEast(coordinate);
                } else {
                    moveNorthEast(coordinate);
                }
            } else {
                if (coordinate.y < 0) {
                    moveSouth(coordinate);
                } else if (coordinate.y > 0){
                    moveNorth(coordinate);
                }
            }

            System.out.println(coordinate);

            steps++;
        }

        System.out.println(steps);
    }

    private static void moveSouthEast(Coordinate coordinate) {
        if (coordinate.x% 2 == 0) {
            coordinate.x--;
        } else {
            coordinate.x--;
            coordinate.y++;
        }
    }

    private static void moveSouthWest(Coordinate coordinate) {
        if (coordinate.x% 2 == 0) {
            coordinate.x++;
        } else {
            coordinate.x++;
            coordinate.y++;
        }
    }

    private static void moveNorthEast(Coordinate coordinate) {
        if (coordinate.x% 2 == 0) {
            coordinate.x--;
            coordinate.y--;
        } else {
            coordinate.x--;
        }
    }

    private static void moveNorthWest(Coordinate coordinate) {
        if (coordinate.x% 2 == 0) {
            coordinate.x++;
            coordinate.y--;
        } else {
            coordinate.x++;
        }
    }

    private static void moveNorth(Coordinate coordinate) {
        coordinate.y--;
    }

    private static void moveSouth(Coordinate coordinate) {
        coordinate.y++;
    }

    private static class Coordinate {
        int x;
        int y;

        @Override
        public String toString() {
            return "Coordinate{" + "x=" + x + ", y=" + y + '}';
        }
    }
}
