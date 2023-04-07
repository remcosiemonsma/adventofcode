package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day11 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var coordinate = new Coordinate();

        input.map(s -> s.split(","))
             .flatMap(Arrays::stream)
             .forEach(s -> {
                 switch (s) {
                     case "n" -> moveNorth(coordinate);
                     case "s" -> moveSouth(coordinate);
                     case "nw" -> moveNorthWest(coordinate);
                     case "ne" -> moveNorthEast(coordinate);
                     case "sw" -> moveSouthWest(coordinate);
                     case "se" -> moveSouthEast(coordinate);
                 }
             });

        return determineDistance(coordinate);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var coordinate = new Coordinate();

        var distanceContainer = new AtomicInteger(Integer.MIN_VALUE);
        
        input.map(s -> s.split(","))
             .flatMap(Arrays::stream)
             .forEach(s -> {
                 switch (s) {
                     case "n" -> moveNorth(coordinate);
                     case "s" -> moveSouth(coordinate);
                     case "nw" -> moveNorthWest(coordinate);
                     case "ne" -> moveNorthEast(coordinate);
                     case "sw" -> moveSouthWest(coordinate);
                     case "se" -> moveSouthEast(coordinate);
                 }

                 var distance = determineDistance(coordinate);

                 if (distance > distanceContainer.get()) {
                     distanceContainer.set(distance);
                 }
             });

        return distanceContainer.get();
    }

    private void moveSouthEast(Coordinate coordinate) {
        if (coordinate.x % 2 == 0) {
            coordinate.x--;
        } else {
            coordinate.x--;
            coordinate.y++;
        }
    }

    private void moveSouthWest(Coordinate coordinate) {
        if (coordinate.x % 2 == 0) {
            coordinate.x++;
        } else {
            coordinate.x++;
            coordinate.y++;
        }
    }

    private void moveNorthEast(Coordinate coordinate) {
        if (coordinate.x % 2 == 0) {
            coordinate.x--;
            coordinate.y--;
        } else {
            coordinate.x--;
        }
    }

    private void moveNorthWest(Coordinate coordinate) {
        if (coordinate.x % 2 == 0) {
            coordinate.x++;
            coordinate.y--;
        } else {
            coordinate.x++;
        }
    }

    private void moveNorth(Coordinate coordinate) {
        coordinate.y--;
    }

    private void moveSouth(Coordinate coordinate) {
        coordinate.y++;
    }

    private int determineDistance(Coordinate coordinate) {
        var steps = 0;

        var coordinateCopy = new Coordinate();
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
                } else {
                    moveNorth(coordinateCopy);
                }
            }

            steps++;
        }

        return steps;
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
