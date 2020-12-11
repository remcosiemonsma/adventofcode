package nl.remcoder.adventofcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day11 {
    public long handlePart1(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        char[][] previous = Arrays.stream(grid)
                                  .map(char[]::clone)
                                  .toArray(char[][]::new);

        boolean finished = false;

        while (!finished) {
            char[][] newLayout = new char[grid.length][grid[0].length];

            for (int yPosition = 0; yPosition < grid.length; yPosition++) {
                for (int xPosition = 0; xPosition < grid[0].length; xPosition++) {
                    int amountAdjacentSeatsFilled = 0;

                    if (yPosition > 0) {
                        if (xPosition > 0) {
                            if (previous[yPosition - 1][xPosition - 1] == '#') {
                                amountAdjacentSeatsFilled++;
                            }
                        }
                        if (previous[yPosition - 1][xPosition] == '#') {
                            amountAdjacentSeatsFilled++;
                        }
                        if (xPosition < grid[0].length - 1) {
                            if (previous[yPosition - 1][xPosition + 1] == '#') {
                                amountAdjacentSeatsFilled++;
                            }
                        }
                    }
                    if (xPosition > 0) {
                        if (previous[yPosition][xPosition - 1] == '#') {
                            amountAdjacentSeatsFilled++;
                        }
                    }
                    if (xPosition < grid[0].length - 1) {
                        if (previous[yPosition][xPosition + 1] == '#') {
                            amountAdjacentSeatsFilled++;
                        }
                    }
                    if (yPosition < grid.length - 1) {
                        if (xPosition > 0) {
                            if (previous[yPosition + 1][xPosition - 1] == '#') {
                                amountAdjacentSeatsFilled++;
                            }
                        }
                        if (previous[yPosition + 1][xPosition] == '#') {
                            amountAdjacentSeatsFilled++;
                        }
                        if (xPosition < grid[0].length - 1) {
                            if (previous[yPosition + 1][xPosition + 1] == '#') {
                                amountAdjacentSeatsFilled++;
                            }
                        }
                    }

                    if (previous[yPosition][xPosition] == '.') {
                        newLayout[yPosition][xPosition] = '.';
                    } else if (previous[yPosition][xPosition] == 'L') {
                        if (amountAdjacentSeatsFilled == 0) {
                            newLayout[yPosition][xPosition] = '#';
                        } else {
                            newLayout[yPosition][xPosition] = 'L';
                        }
                    } else if (previous[yPosition][xPosition] == '#') {
                        if (amountAdjacentSeatsFilled >= 4) {
                            newLayout[yPosition][xPosition] = 'L';
                        } else {
                            newLayout[yPosition][xPosition] = '#';
                        }
                    }
                }
            }

            if (Arrays.deepEquals(previous, newLayout)) {
                finished = true;
            }

            previous = newLayout;
        }

        return Arrays.stream(previous)
                     .map(line -> new String(line).chars())
                     .flatMap(IntStream::boxed)
                     .filter(character -> character == '#')
                     .count();
    }

    public long handlePart2(Stream<String> input) {
        char[][] grid = input.map(String::toCharArray)
                             .toArray(char[][]::new);

        char[][] previous = Arrays.stream(grid)
                                  .map(char[]::clone)
                                  .toArray(char[][]::new);

        boolean finished = false;

        while (!finished) {
            char[][] newLayout = new char[grid.length][grid[0].length];

            for (int yPosition = 0; yPosition < grid.length; yPosition++) {
                for (int xPosition = 0; xPosition < grid[0].length; xPosition++) {
                    if (previous[yPosition][xPosition] == '.') {
                        newLayout[yPosition][xPosition] = '.';
                        continue;
                    }

                    int amountSeatsInSightFilled = 0;

                    for (int y = yPosition - 1, x = xPosition - 1; y >= 0 && x >= 0; y--, x--) {
                        if (previous[y][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][x] == 'L') {
                            break;
                        }
                    }
                    for (int y = yPosition + 1, x = xPosition + 1; y < grid.length && x < grid[0].length; y++, x++) {
                        if (previous[y][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][x] == 'L') {
                            break;
                        }
                    }

                    for (int y = yPosition - 1, x = xPosition + 1; y >= 0 && x < grid[0].length; y--, x++) {
                        if (previous[y][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][x] == 'L') {
                            break;
                        }
                    }
                    for (int y = yPosition + 1, x = xPosition - 1; y < grid.length && x >= 0; y++, x--) {
                        if (previous[y][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][x] == 'L') {
                            break;
                        }
                    }

                    for (int x = xPosition - 1; x >= 0; x--) {
                        if (previous[yPosition][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[yPosition][x] == 'L') {
                            break;
                        }
                    }
                    for (int x = xPosition + 1; x < grid[0].length; x++) {
                        if (previous[yPosition][x] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[yPosition][x] == 'L') {
                            break;
                        }
                    }

                    for (int y = yPosition - 1; y >= 0; y--) {
                        if (previous[y][xPosition] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][xPosition] == 'L') {
                            break;
                        }
                    }
                    for (int y = yPosition + 1; y < grid.length; y++) {
                        if (previous[y][xPosition] == '#') {
                            amountSeatsInSightFilled++;
                            break;
                        } else if (previous[y][xPosition] == 'L') {
                            break;
                        }
                    }

                    if (previous[yPosition][xPosition] == 'L') {
                        if (amountSeatsInSightFilled == 0) {
                            newLayout[yPosition][xPosition] = '#';
                        } else {
                            newLayout[yPosition][xPosition] = 'L';
                        }
                    } else if (previous[yPosition][xPosition] == '#') {
                        if (amountSeatsInSightFilled >= 5) {
                            newLayout[yPosition][xPosition] = 'L';
                        } else {
                            newLayout[yPosition][xPosition] = '#';
                        }
                    }
                }
            }

            if (Arrays.deepEquals(previous, newLayout)) {
                finished = true;
            }

            previous = newLayout;
        }

        return Arrays.stream(previous)
                     .map(line -> new String(line).chars())
                     .flatMap(IntStream::boxed)
                     .filter(character -> character == '#')
                     .count();
    }

    private void printGrid(char[][] grid) {
        for (char[] line : grid) {
            for (char c : line) {
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
}
