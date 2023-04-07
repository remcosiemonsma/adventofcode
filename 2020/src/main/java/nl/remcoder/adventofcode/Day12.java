package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day12 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var movements = input.map(this::parseStringToMovement)
                             .toList();

        var x = 0;
        var y = 0;

        var shipDirection = ShipDirection.EAST;

        for (var movement : movements) {
            switch (movement.direction) {
                case 'N' -> y += movement.amount;
                case 'S' -> y -= movement.amount;
                case 'E' -> x += movement.amount;
                case 'W' -> x -= movement.amount;
                case 'F' -> {
                    switch (shipDirection) {
                        case NORTH -> y += movement.amount;
                        case SOUTH -> y -= movement.amount;
                        case EAST -> x += movement.amount;
                        case WEST -> x -= movement.amount;
                    }
                }
                case 'L' -> {
                    int turns = movement.amount / 90;

                    for (int i = 0; i < turns; i++) {
                        switch (shipDirection) {
                            case NORTH -> shipDirection = ShipDirection.WEST;
                            case SOUTH -> shipDirection = ShipDirection.EAST;
                            case EAST -> shipDirection = ShipDirection.NORTH;
                            case WEST -> shipDirection = ShipDirection.SOUTH;
                        }
                    }
                }
                case 'R' -> {
                    int turns = movement.amount / 90;

                    for (int i = 0; i < turns; i++) {
                        switch (shipDirection) {
                            case NORTH -> shipDirection = ShipDirection.EAST;
                            case SOUTH -> shipDirection = ShipDirection.WEST;
                            case EAST -> shipDirection = ShipDirection.SOUTH;
                            case WEST -> shipDirection = ShipDirection.NORTH;
                        }
                    }
                }
            }
        }

        return Math.abs(x) + Math.abs(y);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var movements = input.map(this::parseStringToMovement)
                             .toList();

        var shipX = 0;
        var shipY = 0;
        var waypointX = 10;
        var waypointY = 1;

        for (var movement : movements) {
            switch (movement.direction) {
                case 'N' -> waypointY += movement.amount;
                case 'S' -> waypointY -= movement.amount;
                case 'E' -> waypointX += movement.amount;
                case 'W' -> waypointX -= movement.amount;
                case 'F' -> {
                    for (int i = 0; i < movement.amount; i++) {
                        shipX += waypointX;
                        shipY += waypointY;
                    }
                }
                case 'L' -> {
                    int turns = movement.amount / 90;

                    for (int i = 0; i < turns; i++) {
                        int tmp = waypointX;
                        waypointX = -waypointY;
                        waypointY = tmp;
                    }
                }
                case 'R' -> {
                    int turns = movement.amount / 90;

                    for (int i = 0; i < turns; i++) {
                        int tmp = waypointX;
                        waypointX = waypointY;
                        waypointY = -tmp;
                    }
                }
            }
        }

        return Math.abs(shipX) + Math.abs(shipY);
    }

    private Movement parseStringToMovement(String string) {
        char direction = string.charAt(0);
        int amount = Integer.parseInt(string.substring(1));

        return new Movement(direction, amount);
    }

    private enum ShipDirection {
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    private record Movement(char direction, int amount) {
    }
}
