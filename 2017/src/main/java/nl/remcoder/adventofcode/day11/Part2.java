package nl.remcoder.adventofcode.day11;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Part2 {
    public static void main(String[] args) throws Exception {
        Coordinate coordinate = new Coordinate();

        coordinate.x = 0;
        coordinate.y = 0;

        DistanceContainer distanceContainer = new DistanceContainer();
        distanceContainer.distance = 0;

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

            int distance = determineDistance(coordinate);

            if (distance > distanceContainer.distance) {
                distanceContainer.distance = distance;
            }
        });

        System.out.println(distanceContainer.distance);
    }

    private static int determineDistance(Coordinate coordinate) {
        int steps = 0;

        Coordinate coordinateCopy = new Coordinate();
        coordinateCopy.x = coordinate.x;
        coordinateCopy.y = coordinate.y;

        while (coordinateCopy.x != 0 || coordinateCopy.y != 0) {
            if (coordinateCopy.x < 0) {
                if (coordinateCopy.y < 0) {
                    moveSouthWest(coordinateCopy);
                } else if (coordinateCopy.y > 0){
                    moveNorthWest(coordinateCopy);
                } else {
                    moveNorthWest(coordinateCopy);
                }
            } else if (coordinateCopy.x > 0) {
                if (coordinateCopy.y < 0) {
                    moveSouthEast(coordinateCopy);
                } else if (coordinateCopy.y > 0){
                    moveNorthEast(coordinateCopy);
                } else {
                    moveNorthEast(coordinateCopy);
                }
            } else {
                if (coordinateCopy.y < 0) {
                    moveSouth(coordinateCopy);
                } else if (coordinateCopy.y > 0){
                    moveNorth(coordinateCopy);
                }
            }

            steps++;
        }

        return steps;
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

    private static class DistanceContainer {
        int distance;
    }
}
