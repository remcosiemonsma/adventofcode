package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .mapToObj(i -> (char) i)
                                   .map(this::parseLumberSpot)
                                   .toArray(LumberSpot[]::new))
                        .toArray(LumberSpot[][]::new);

        for (var minute = 0; minute < 10; minute++) {
            for (var y = 0; y < grid.length; y++) {
                for (var x = 0; x < grid[y].length; x++) {
                    var lumberSpot = grid[y][x];
                    var amountOfTrees = countTrees(grid, y, x);
                    var amountOfLumberYards = countLumberYards(grid, y, x);
                    switch (lumberSpot.lumberState) {
                        case OPEN_GROUND -> {
                            if (amountOfTrees >= 3) {
                                lumberSpot.canChange = true;
                            }
                        }
                        case TREES -> {
                            if (amountOfLumberYards >= 3) {
                                lumberSpot.canChange = true;
                            }
                        }
                        case LUMBERYARD -> {
                            if (amountOfTrees == 0 || amountOfLumberYards == 0) {
                                lumberSpot.canChange = true;
                            }
                        }
                    }
                }
            }

            for (var lumberSpots : grid) {
                for (var lumberSpot : lumberSpots) {
                    lumberSpot.change();
                }
            }
        }

        return countGridValue(grid);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        var grid = input.map(s -> s.chars()
                                   .mapToObj(i -> (char) i)
                                   .map(this::parseLumberSpot)
                                   .toArray(LumberSpot[]::new))
                        .toArray(LumberSpot[][]::new);

        var previousStates = new ArrayList<LumberStateContainer>();

        previousStates.add(new LumberStateContainer(translateGridToStates(grid)));

        for (var minute = 0; minute < 1000000000; minute++) {
            for (var y = 0; y < grid.length; y++) {
                for (var x = 0; x < grid[y].length; x++) {
                    var lumberSpot = grid[y][x];
                    var amountOfTrees = countTrees(grid, y, x);
                    var amountOfLumberYards = countLumberYards(grid, y, x);
                    switch (lumberSpot.lumberState) {
                        case OPEN_GROUND -> {
                            if (amountOfTrees >= 3) {
                                lumberSpot.canChange = true;
                            }
                        }
                        case TREES -> {
                            if (amountOfLumberYards >= 3) {
                                lumberSpot.canChange = true;
                            }
                        }
                        case LUMBERYARD -> {
                            if (amountOfTrees == 0 || amountOfLumberYards == 0) {
                                lumberSpot.canChange = true;
                            }
                        }
                    }
                }
            }

            for (var lumberSpots : grid) {
                for (var lumberSpot : lumberSpots) {
                    lumberSpot.change();
                }
            }

            var lumberStateContainer = new LumberStateContainer(translateGridToStates(grid));

            if (!previousStates.contains(lumberStateContainer)) {
                previousStates.add(lumberStateContainer);
            } else {
                int distance = previousStates.size() - previousStates.indexOf(lumberStateContainer);

                int remainder = 1000000000 - previousStates.indexOf(lumberStateContainer);

                int offset = remainder % distance;

                return countGridValue(
                        previousStates.get(previousStates.indexOf(lumberStateContainer) + offset).lumberStates);
            }
        }
        return null;
    }

    private int countGridValue(LumberState[][] grid) {
        var amountOfTrees = 0;
        var amountOfLumberYards = 0;
        for (var lumberStates : grid) {
            for (var lumberState : lumberStates) {
                switch (lumberState) {
                    case OPEN_GROUND:
                        break;
                    case TREES:
                        amountOfTrees++;
                        break;
                    case LUMBERYARD:
                        amountOfLumberYards++;
                        break;
                }
            }
        }

        return amountOfLumberYards * amountOfTrees;
    }

    private LumberState[][] translateGridToStates(LumberSpot[][] grid) {
        var lumberStates = new LumberState[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                lumberStates[y][x] = grid[y][x].lumberState;
            }
        }

        return lumberStates;
    }

    private LumberSpot parseLumberSpot(char c) {
        var lumberSpot = new LumberSpot();
        lumberSpot.lumberState = switch (c) {
            case '.' -> LumberState.OPEN_GROUND;
            case '|' -> LumberState.TREES;
            case '#' -> LumberState.LUMBERYARD;
            default -> throw new AssertionError("Eek!");
        };
        return lumberSpot;
    }

    private int countGridValue(LumberSpot[][] grid) {
        var amountOfTrees = 0;
        var amountOfLumberYards = 0;
        for (var lumberSpots : grid) {
            for (var lumberSpot : lumberSpots) {
                switch (lumberSpot.lumberState) {
                    case OPEN_GROUND:
                        break;
                    case TREES:
                        amountOfTrees++;
                        break;
                    case LUMBERYARD:
                        amountOfLumberYards++;
                        break;
                }
            }
        }

        return amountOfLumberYards * amountOfTrees;
    }

    private int countTrees(LumberSpot[][] grid, int y, int x) {
        var amountOfTrees = 0;

        if (y != 0) {
            if (x != 0) {
                if (grid[y - 1][x - 1].lumberState == LumberState.TREES) {
                    amountOfTrees++;
                }
            }
            if (grid[y - 1][x].lumberState == LumberState.TREES) {
                amountOfTrees++;
            }
            if (x != grid[y].length - 1) {
                if (grid[y - 1][x + 1].lumberState == LumberState.TREES) {
                    amountOfTrees++;
                }
            }
        }
        if (x != 0) {
            if (grid[y][x - 1].lumberState == LumberState.TREES) {
                amountOfTrees++;
            }
        }
        if (x != grid[y].length - 1) {
            if (grid[y][x + 1].lumberState == LumberState.TREES) {
                amountOfTrees++;
            }
        }
        if (y != grid.length - 1) {
            if (x != 0) {
                if (grid[y + 1][x - 1].lumberState == LumberState.TREES) {
                    amountOfTrees++;
                }
            }
            if (grid[y + 1][x].lumberState == LumberState.TREES) {
                amountOfTrees++;
            }
            if (x != grid[y].length - 1) {
                if (grid[y + 1][x + 1].lumberState == LumberState.TREES) {
                    amountOfTrees++;
                }
            }
        }
        return amountOfTrees;
    }

    private int countLumberYards(LumberSpot[][] grid, int y, int x) {
        var amountOfLumberYards = 0;

        if (y != 0) {
            if (x != 0) {
                if (grid[y - 1][x - 1].lumberState == LumberState.LUMBERYARD) {
                    amountOfLumberYards++;
                }
            }
            if (grid[y - 1][x].lumberState == LumberState.LUMBERYARD) {
                amountOfLumberYards++;
            }
            if (x != grid[y].length - 1) {
                if (grid[y - 1][x + 1].lumberState == LumberState.LUMBERYARD) {
                    amountOfLumberYards++;
                }
            }
        }
        if (x != 0) {
            if (grid[y][x - 1].lumberState == LumberState.LUMBERYARD) {
                amountOfLumberYards++;
            }
        }
        if (x != grid[y].length - 1) {
            if (grid[y][x + 1].lumberState == LumberState.LUMBERYARD) {
                amountOfLumberYards++;
            }
        }
        if (y != grid.length - 1) {
            if (x != 0) {
                if (grid[y + 1][x - 1].lumberState == LumberState.LUMBERYARD) {
                    amountOfLumberYards++;
                }
            }
            if (grid[y + 1][x].lumberState == LumberState.LUMBERYARD) {
                amountOfLumberYards++;
            }
            if (x != grid[y].length - 1) {
                if (grid[y + 1][x + 1].lumberState == LumberState.LUMBERYARD) {
                    amountOfLumberYards++;
                }
            }
        }
        return amountOfLumberYards;
    }

    private record LumberStateContainer(LumberState[][] lumberStates) {
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof LumberStateContainer that)) {
                return false;
            }

            return Arrays.deepEquals(lumberStates, that.lumberStates);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(lumberStates);
        }
    }

    private static class LumberSpot {
        LumberState lumberState;
        boolean canChange;

        void change() {
            if (canChange) {
                lumberState = switch (lumberState) {
                    case OPEN_GROUND -> LumberState.TREES;
                    case TREES -> LumberState.LUMBERYARD;
                    case LUMBERYARD -> LumberState.OPEN_GROUND;
                };
            }
            canChange = false;
        }
    }

    private enum LumberState {
        OPEN_GROUND,
        TREES,
        LUMBERYARD
    }
}
