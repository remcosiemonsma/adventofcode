package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.stream.Stream;

public class Day22 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .mapToObj(c -> c == '#')
                                   .toArray(Boolean[]::new))
                        .toArray(Boolean[][]::new);

        var posx = (int) Math.ceil(grid.length / 2d) - 1;
        var posy = (int) Math.ceil(grid.length / 2d) - 1;

        var direction = Direction.NORTH;

        var infections = 0;

        for (var counter = 0; counter < 10000; counter++) {
            if (Boolean.TRUE.equals(grid[posx][posy])) {
                direction = switch (direction) {
                    case NORTH -> Direction.EAST;
                    case EAST -> Direction.SOUTH;
                    case SOUTH -> Direction.WEST;
                    case WEST -> Direction.NORTH;
                };
                grid[posx][posy] = false;
            } else {
                direction = switch (direction) {
                    case NORTH -> Direction.WEST;
                    case EAST -> Direction.NORTH;
                    case SOUTH -> Direction.EAST;
                    case WEST -> Direction.SOUTH;
                };
                grid[posx][posy] = true;
                infections++;
            }
            switch (direction) {
                case NORTH -> posx--;
                case SOUTH -> posx++;
                case EAST -> posy++;
                case WEST -> posy--;
            }
            if (0 > posx || posx >= grid.length) {
                grid = enlargeGrid(grid);

                posx++;
                posy++;
            }
            if (0 > posy || posy >= grid.length) {
                grid = enlargeGrid(grid);

                posy++;
                posx++;
            }
        }

        return infections;
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .mapToObj(c -> {
                                       if (c == '#') {
                                           return InfectionState.INFECTED;
                                       } else {
                                           return InfectionState.CLEAN;
                                       }
                                   })
                                   .toArray(InfectionState[]::new))
                        .toArray(InfectionState[][]::new);

        var posx = (int)Math.ceil(grid.length / 2d) - 1;
        var posy = (int)Math.ceil(grid.length / 2d) - 1;

        var direction = Direction.NORTH;

        var infections = 0;

        for (var counter = 0; counter < 10000000; counter++) {
            if (grid[posx][posy] == null) {
                grid[posx][posy] = InfectionState.CLEAN;
            }

            switch (grid[posx][posy]) {
                case CLEAN -> {
                    direction = switch (direction) {
                        case NORTH -> Direction.WEST;
                        case EAST -> Direction.NORTH;
                        case SOUTH -> Direction.EAST;
                        case WEST -> Direction.SOUTH;
                    };
                    grid[posx][posy] = InfectionState.WEAKENED;
                }
                case WEAKENED -> {
                    grid[posx][posy] = InfectionState.INFECTED;
                    infections++;
                }
                case INFECTED -> {
                    direction = switch (direction) {
                        case NORTH -> Direction.EAST;
                        case EAST -> Direction.SOUTH;
                        case SOUTH -> Direction.WEST;
                        case WEST -> Direction.NORTH;
                    };
                    grid[posx][posy] = InfectionState.FLAGGED;
                }
                case FLAGGED -> {
                    direction = switch (direction) {
                        case NORTH -> Direction.SOUTH;
                        case EAST -> Direction.WEST;
                        case SOUTH -> Direction.NORTH;
                        case WEST -> Direction.EAST;
                    };
                    grid[posx][posy] = InfectionState.CLEAN;
                }
            }
            switch (direction) {
                case NORTH -> posx--;
                case SOUTH -> posx++;
                case EAST -> posy++;
                case WEST -> posy--;
            }
            if (0 > posx || posx >= grid.length) {
                grid = enlargeGrid(grid);

                posx++;
                posy++;
            }
            if (0 > posy || posy >= grid.length) {
                grid = enlargeGrid(grid);

                posy++;
                posx++;
            }
        }
        
        return infections;
    }

    private Boolean[][] enlargeGrid(Boolean[][] grid) {
        var newgrid = new Boolean[grid.length + 2][grid.length + 2];

        for (var i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newgrid[i + 1], 1, grid.length);
        }

        return newgrid;
    }

    private InfectionState[][] enlargeGrid(InfectionState[][] grid) {
        var newgrid = new InfectionState[grid.length + 2][grid.length + 2];

        for (int i = 0; i < grid.length; i++) {
            System.arraycopy(grid[i], 0, newgrid[i + 1], 1, grid.length);
        }

        grid = newgrid;
        return grid;
    }

    private enum Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }

    private enum InfectionState {
        WEAKENED,
        INFECTED,
        FLAGGED,
        CLEAN
    }
}
