package nl.remcoder.adventofcode.day19;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {
    private static int x;
    private static int y;

    public static void main(String[] args) throws Exception {
        List<String> input =
                Files.readAllLines(Paths.get(ClassLoader.getSystemResource("day19/input").toURI()));

        char[][] grid = new char[input.size()][];

        for (int i =0; i < input.size(); i++) {
            grid[i] = input.get(i).toCharArray();
        }

        y = input.get(0).indexOf('|');
        x = 0;

        StringBuilder resultBuilder = new StringBuilder();

        Direction direction = Direction.SOUTH;

        while (direction != null) {
            switch (direction) {
                case SOUTH:
                    direction = moveSouth(grid, resultBuilder);
                    break;
                case NORTH:
                    direction = moveNorth(grid, resultBuilder);
                    break;
                case EAST:
                    direction = moveEast(grid, resultBuilder);
                    break;
                case WEST:
                    direction = moveWest(grid, resultBuilder);
                    break;
            }
        }

        System.out.println(resultBuilder.toString());
    }

    private enum Direction {
        NORTH,
        SOUTH,
        WEST,
        EAST
    }

    private static Direction moveSouth(char[][] grid, StringBuilder resultBuilder) {
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

    private static Direction moveNorth(char[][] grid, StringBuilder resultBuilder) {
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

    private static Direction moveEast(char[][] grid, StringBuilder resultBuilder) {
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

    private static Direction moveWest(char[][] grid, StringBuilder resultBuilder) {
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
