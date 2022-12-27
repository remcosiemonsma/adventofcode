package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.BiAdventOfCodeSolution;

import java.util.stream.Stream;

public class Day19 implements BiAdventOfCodeSolution<String, Integer> {
    private int x;
    private int y;

    @Override
    public String handlePart1(Stream<String> input) {
        var lines = input.toList();
        
        var grid = new char[lines.size()][];

        for (var i =0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        y = lines.get(0).indexOf('|');
        x = 0;

        var resultBuilder = new StringBuilder();

        var direction = Direction.SOUTH;

        while (direction != null) {
            direction = switch (direction) {
                case SOUTH -> moveSouth(grid, resultBuilder);
                case NORTH -> moveNorth(grid, resultBuilder);
                case EAST -> moveEast(grid, resultBuilder);
                case WEST -> moveWest(grid, resultBuilder);
            };
        }
        
        return resultBuilder.toString();
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var lines = input.toList();
        
        var grid = new char[lines.size()][];

        for (var i =0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }

        y = lines.get(0).indexOf('|');
        x = 0;

        var resultBuilder = new StringBuilder();

        var direction = Direction.SOUTH;

        var steps = 0;

        while (direction != null) {
            direction = switch (direction) {
                case SOUTH -> moveSouth(grid, resultBuilder);
                case NORTH -> moveNorth(grid, resultBuilder);
                case EAST -> moveEast(grid, resultBuilder);
                case WEST -> moveWest(grid, resultBuilder);
            };
            if (direction != null) {
                steps++;
            }
        }
        
        return steps;
    }

    private enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private Direction moveSouth(char[][] grid, StringBuilder resultBuilder) {
        if (x >= grid.length) {
            //endpoint encountered
            return null;
        }

        if (grid[x][y] == '|' || grid[x][y] == '-') {
            x++;
            return Direction.SOUTH;
        } else if (grid[x][y] == '+') {
            if (y > 0 && grid[x][y - 1] != ' ') {
                y--;
                return Direction.WEST;
            } else if (y < grid[x].length - 1 && grid[x][y + 1] != ' ') {
                y++;
                return Direction.EAST;
            } else {
                throw new RuntimeException("Encountered dead end! at x: " + x + ", y:" + y);
            }
        } else if (grid[x][y] == ' ') {
            throw new RuntimeException("Encountered empty spot! at x: " + x + ", y:" + y);
        } else {
            resultBuilder.append(grid[x][y]);
            x++;
            return Direction.SOUTH;
        }
    }

    private Direction moveNorth(char[][] grid, StringBuilder resultBuilder) {
        if (x < 0) {
            //endpoint encountered
            return null;
        }

        if (grid[x][y] == '|' || grid[x][y] == '-') {
            x--;
            return Direction.NORTH;
        } else if (grid[x][y] == '+') {
            if (y > 0 && grid[x][y - 1] != ' ') {
                y--;
                return Direction.WEST;
            } else if (y < grid[x].length - 1 && grid[x][y + 1] != ' ') {
                y++;
                return Direction.EAST;
            } else {
                throw new RuntimeException("Encountered dead end! at x: " + x + ", y:" + y);
            }
        } else if (grid[x][y] == ' ') {
            throw new RuntimeException("Encountered empty spot! at x: " + x + ", y:" + y);
        } else {
            resultBuilder.append(grid[x][y]);
            x--;
            return Direction.NORTH;
        }
    }

    private Direction moveEast(char[][] grid, StringBuilder resultBuilder) {
        if (y >= grid[x].length) {
            //endpoint encountered
            return null;
        }

        if (grid[x][y] == '-' || grid[x][y] == '|') {
            y++;
            return Direction.EAST;
        } else if (grid[x][y] == '+') {
            if (x > 0 && y < grid[x - 1].length && grid[x - 1][y] != ' ') {
                x--;
                return Direction.NORTH;
            } else if (x < grid.length - 1 && y < grid[x + 1].length && grid[x + 1][y] != ' ') {
                x++;
                return Direction.SOUTH;
            } else {
                throw new RuntimeException("Encountered dead end! at x: " + x + ", y:" + y);
            }
        } else if (grid[x][y] == ' ') {
            throw new RuntimeException("Encountered empty spot! at x: " + x + ", y:" + y);
        } else {
            resultBuilder.append(grid[x][y]);
            y++;
            return Direction.EAST;
        }
    }

    private Direction moveWest(char[][] grid, StringBuilder resultBuilder) {
        if (y < 0) {
            //endpoint encountered
            return null;
        }

        if (grid[x][y] == '-' || grid[x][y] == '|') {
            y--;
            return Direction.WEST;
        } else if (grid[x][y] == '+') {
            if (x > 0 && y < grid[x - 1].length && grid[x - 1][y] != ' ') {
                x--;
                return Direction.NORTH;
            } else if (y < grid[x + 1].length && grid[x + 1][y] != ' ') {
                x++;
                return Direction.SOUTH;
            } else {
                throw new RuntimeException("Encountered dead end! at x: " + x + ", y:" + y);
            }
        } else if (grid[x][y] == ' ') {
            throw new RuntimeException("Encountered empty spot! at x: " + x + ", y:" + y);
        } else {
            resultBuilder.append(grid[x][y]);
            y--;
            return Direction.WEST;
        }
    }
}
