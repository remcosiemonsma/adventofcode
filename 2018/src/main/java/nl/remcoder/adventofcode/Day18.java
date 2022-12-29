package nl.remcoder.adventofcode;

import nl.remcoder.adventofcode.library.AdventOfCodeSolution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Day18 implements AdventOfCodeSolution<Integer> {
    @Override
    public Integer handlePart1(Stream<String> input) {
        LumberSpot[][] grid = input.map(s -> s.chars()
                                              .mapToObj(i -> (char) i)
                                              .map(this::parseLumberSpot)
                                              .toArray(LumberSpot[]::new))
                                   .toArray(LumberSpot[][]::new);

        for (int minute = 0; minute < 10; minute++) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    LumberSpot lumberSpot = grid[y][x];
                    int amountOfTrees = countTrees(grid, y, x);
                    int amountOfLumberYards = countLumberYards(grid, y, x);
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

            for (LumberSpot[] lumberSpots : grid) {
                for (LumberSpot lumberSpot : lumberSpots) {
                    lumberSpot.change();
                }
            }
        }

        return countGridValue(grid);
    }

    @Override
    public Integer handlePart2(Stream<String> input) {
        LumberSpot[][] grid = input.map(s -> s.chars()
                                              .mapToObj(i -> (char) i)
                                              .map(this::parseLumberSpot)
                                              .toArray(LumberSpot[]::new))
                                   .toArray(LumberSpot[][]::new);

        List<LumberStateContainer> previousStates = new ArrayList<>();

        previousStates.add(new LumberStateContainer(translateGridToStates(grid)));

        for (int minute = 0; minute < 1000000000; minute++) {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    LumberSpot lumberSpot = grid[y][x];
                    int amountOfTrees = countTrees(grid, y, x);
                    int amountOfLumberYards = countLumberYards(grid, y, x);
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

            for (LumberSpot[] lumberSpots : grid) {
                for (LumberSpot lumberSpot : lumberSpots) {
                    lumberSpot.change();
                }
            }

            LumberStateContainer lumberStateContainer = new LumberStateContainer(translateGridToStates(grid));

            if (!previousStates.contains(lumberStateContainer)) {
                previousStates.add(lumberStateContainer);
            } else {
                int distance = previousStates.size() - previousStates.indexOf(lumberStateContainer);

                int remainder = 1000000000 - previousStates.indexOf(lumberStateContainer);

                int offset = remainder % distance;

                return countGridValue(previousStates.get(previousStates.indexOf(lumberStateContainer) + offset).lumberStates);
            }
        }
        return null;
    }

    private int countGridValue(LumberState[][] grid) {
        int amountOfTrees = 0;
        int amountOfLumberYards = 0;
        for (LumberState[] lumberStates : grid) {
            for (LumberState lumberState : lumberStates) {
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
        LumberState[][] lumberStates = new LumberState[grid.length][grid[0].length];

        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                lumberStates[y][x] = grid[y][x].lumberState;
            }
        }

        return lumberStates;
    }
    
    private LumberSpot parseLumberSpot(char c) {
        LumberSpot lumberSpot = new LumberSpot();
        lumberSpot.lumberState = switch (c) {
            case '.' -> LumberState.OPEN_GROUND;
            case '|' -> LumberState.TREES;
            case '#' -> LumberState.LUMBERYARD;
            default -> throw new AssertionError("Eek!");
        };
        return lumberSpot;
    }

    private int countGridValue(LumberSpot[][] grid) {
        int amountOfTrees = 0;
        int amountOfLumberYards = 0;
        for (LumberSpot[] lumberSpots : grid) {
            for (LumberSpot lumberSpot : lumberSpots) {
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
        int amountOfTrees = 0;

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
        int amountOfLumberYards = 0;

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

    private static class LumberStateContainer {
        final LumberState[][] lumberStates;

        private LumberStateContainer(LumberState[][] lumberStates) {
            this.lumberStates = lumberStates;
        }

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
