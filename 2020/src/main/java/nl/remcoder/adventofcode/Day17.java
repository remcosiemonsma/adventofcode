package nl.remcoder.adventofcode;

import java.util.stream.Stream;

public class Day17 {
    public int handlePart1(Stream<String> input) {
        boolean[][] slice = input.map(this::mapLineToGridLine)
                                 .toArray(boolean[][]::new);

        boolean[][][] grid = new boolean[24][24][24];

        boolean[][] shiftedSlice = new boolean[24][24];

        for (int position = 0; position < slice.length; position++) {
            boolean[] line = new boolean[24];

            System.arraycopy(slice[position], 0, line, 12 - (slice[position].length / 2), slice[position].length);

            shiftedSlice[12 - (slice.length / 2) + position] = line;
        }

        grid[12] = shiftedSlice;

        for (int cycle = 0; cycle < 6; cycle++) {
            grid = activateCubes(grid);
        }

        return countActiveCubes(grid);
    }

    public int handlePart2(Stream<String> input) {
        boolean[][] slice = input.map(this::mapLineToGridLine)
                                 .toArray(boolean[][]::new);

        boolean[][][][] grid = new boolean[24][24][24][24];

        boolean[][] shiftedSlice = new boolean[24][24];

        for (int position = 0; position < slice.length; position++) {
            boolean[] line = new boolean[24];

            System.arraycopy(slice[position], 0, line, 12 - (slice[position].length / 2), slice[position].length);

            shiftedSlice[12 - (slice.length / 2) + position] = line;
        }

        grid[12][12] = shiftedSlice;

        for (int cycle = 0; cycle < 6; cycle++) {
            grid = activateCubes(grid);
        }

        return countActiveCubes(grid);
    }

    private boolean[][][][] activateCubes(boolean[][][][] grid) {
        boolean[][][][] newGrid = new boolean[grid.length][grid[0].length][grid[0][0].length][grid[0][0][0].length];

        for (int wPosition = 0; wPosition < grid.length; wPosition++) {
            for (int zPosition = 0; zPosition < grid[0].length; zPosition++) {
                for (int yPosition = 0; yPosition < grid[0][0].length; yPosition++) {
                    for (int xPosition = 0; xPosition < grid[0][0][0].length; xPosition++) {
                        int amountOfActiveNeighbors = 0;

                        outer:
                        for (int wIndex = wPosition - 1; wIndex <= wPosition + 1; wIndex++) {
                            if (wIndex < 0 || wIndex >= grid.length) {
                                continue;
                            }
                            for (int zIndex = zPosition - 1; zIndex <= zPosition + 1; zIndex++) {
                                if (zIndex < 0 || zIndex >= grid[0].length) {
                                    continue;
                                }
                                for (int yIndex = yPosition - 1; yIndex <= yPosition + 1; yIndex++) {
                                    if (yIndex < 0 || yIndex >= grid[0][0].length) {
                                        continue;
                                    }
                                    for (int xIndex = xPosition - 1; xIndex <= xPosition + 1; xIndex++) {
                                        if (xIndex < 0 || xIndex >= grid[0][0][0].length) {
                                            continue;
                                        }
                                        if (wIndex == wPosition && zIndex == zPosition && yIndex == yPosition && xIndex == xPosition) {
                                            continue;
                                        }
                                        if (grid[wIndex][zIndex][yIndex][xIndex]) {
                                            amountOfActiveNeighbors++;
                                            if (amountOfActiveNeighbors > 3) {
                                                break outer;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        if (grid[wPosition][zPosition][yPosition][xPosition]) {
                            if (amountOfActiveNeighbors == 2 || amountOfActiveNeighbors == 3) {
                                newGrid[wPosition][zPosition][yPosition][xPosition] = true;
                            }
                        } else {
                            if (amountOfActiveNeighbors == 3) {
                                newGrid[wPosition][zPosition][yPosition][xPosition] = true;
                            }
                        }
                    }
                }
            }
        }

        return newGrid;
    }

    private boolean[][][] activateCubes(boolean[][][] grid) {
        boolean[][][] newGrid = new boolean[grid.length][grid[0].length][grid[0][0].length];

        for (int zPosition = 0; zPosition < grid.length; zPosition++) {
            for (int yPosition = 0; yPosition < grid[0].length; yPosition++) {
                for (int xPosition = 0; xPosition < grid[0][0].length; xPosition++) {
                    int amountOfActiveNeighbors = 0;

                    outer:
                    for (int zIndex = zPosition - 1; zIndex <= zPosition + 1; zIndex++) {
                        if (zIndex < 0 || zIndex >= grid.length) {
                            continue;
                        }
                        for (int yIndex = yPosition - 1; yIndex <= yPosition + 1; yIndex++) {
                            if (yIndex < 0 || yIndex >= grid[0].length) {
                                continue;
                            }
                            for (int xIndex = xPosition - 1; xIndex <= xPosition + 1; xIndex++) {
                                if (xIndex < 0 || xIndex >= grid[0][0].length) {
                                    continue;
                                }
                                if (zIndex == zPosition && yIndex == yPosition && xIndex == xPosition) {
                                    continue;
                                }
                                if (grid[zIndex][yIndex][xIndex]) {
                                    amountOfActiveNeighbors++;
                                    if (amountOfActiveNeighbors > 3) {
                                        break outer;
                                    }
                                }
                            }
                        }
                    }

                    if (grid[zPosition][yPosition][xPosition]) {
                        if (amountOfActiveNeighbors == 2 || amountOfActiveNeighbors == 3) {
                            newGrid[zPosition][yPosition][xPosition] = true;
                        }
                    } else {
                        if (amountOfActiveNeighbors == 3) {
                            newGrid[zPosition][yPosition][xPosition] = true;
                        }
                    }
                }
            }
        }

        return newGrid;
    }

    private int countActiveCubes(boolean[][][] grid) {
        int activeCubes = 0;

        for (boolean[][] slice : grid) {
            for (boolean[] line : slice) {
                for (boolean cube : line) {
                    if (cube) {
                        activeCubes++;
                    }
                }
            }
        }

        return activeCubes;
    }

    private int countActiveCubes(boolean[][][][] grid) {
        int activeCubes = 0;

        for (boolean[][][] cube : grid) {
            for (boolean[][] slice : cube) {
                for (boolean[] line : slice) {
                    for (boolean hyperCube : line) {
                        if (hyperCube) {
                            activeCubes++;
                        }
                    }
                }
            }
        }

        return activeCubes;
    }

    private boolean[] mapLineToGridLine(String line) {
        boolean[] gridLine = new boolean[line.length()];

        for (int position = 0; position < line.length(); position++) {
            gridLine[position] = line.charAt(position) == '#';
        }

        return gridLine;
    }
}
